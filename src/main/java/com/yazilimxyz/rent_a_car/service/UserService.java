package com.yazilimxyz.rent_a_car.service;

import com.yazilimxyz.rent_a_car.dto.UserDTO;
import com.yazilimxyz.rent_a_car.dto.responses.LoginResponse;
import com.yazilimxyz.rent_a_car.entity.User;
import com.yazilimxyz.rent_a_car.entity.enums.Role;
import com.yazilimxyz.rent_a_car.exception.EmailAlreadyExistsException;
import com.yazilimxyz.rent_a_car.exception.MyException;
import com.yazilimxyz.rent_a_car.exception.UserNotFoundException;
import com.yazilimxyz.rent_a_car.repository.UserRepository;
import com.yazilimxyz.rent_a_car.service.interfaces.IUserService;
import com.yazilimxyz.rent_a_car.utils.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }


    @Override
    public UserDTO register(UserDTO userDTO) {
        if (this.userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        userDTO.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        User user = UserMapper.INSTANCE.userDtoToUser(userDTO);
        user.setAuthorities(new HashSet<>(Set.of(Role.ROLE_USER)));
        user.setAccountNonExpired(true);
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        User savedUser = this.userRepository.save(user);

        return UserMapper.INSTANCE.userToUserDto(savedUser);
    }

    @Override
    public LoginResponse login(UserDTO userDTO) {
        LoginResponse response = new LoginResponse();
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
            User user = this.userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() -> new MyException("User not found"));
            var jwt = this.jwtService.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setAuthorities(user.getAuthorities());
            response.setExpirationTime("7 Days");
            response.setMessage("Login successful");
        }
        catch(MyException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
            return response;
        } catch(Exception e) {
            response.setStatusCode(500);
            response.setMessage("Internal server error: " + e.getMessage());
            return response;
        }
        return response;
    }

    @Override
    public List<UserDTO> getAllUsers() {
            List<User> userList = this.userRepository.findAll();
            List<UserDTO> userDtoList = new ArrayList<>();
            userList.forEach(user -> userDtoList.add(UserMapper.INSTANCE.userToUserDto(user)));
            return userDtoList;
    }

    @Override
    public void deleteUser(String userId) {
       User user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException("User not found"));
       this.userRepository.delete(user);
    }

    @Override
    public UserDTO getUserById(String userId) {
        User user = this.userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.INSTANCE.userToUserDto(user);
    }

}
