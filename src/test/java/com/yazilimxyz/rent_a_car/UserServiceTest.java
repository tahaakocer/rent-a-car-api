package com.yazilimxyz.rent_a_car;

import com.yazilimxyz.rent_a_car.dto.UserDTO;
import com.yazilimxyz.rent_a_car.entity.User;
import com.yazilimxyz.rent_a_car.entity.enums.Role;
import com.yazilimxyz.rent_a_car.exception.EmailAlreadyExistsException;
import com.yazilimxyz.rent_a_car.exception.UserNotFoundException;
import com.yazilimxyz.rent_a_car.repository.UserRepository;
import com.yazilimxyz.rent_a_car.service.JWTService;
import com.yazilimxyz.rent_a_car.service.UserService;
import com.yazilimxyz.rent_a_car.utils.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userDTO = new UserDTO();
        userDTO.setId(UUID.randomUUID());
        userDTO.setEmail("test@test.com");
        userDTO.setName("Test User");
        userDTO.setPhoneNumber("1234567890");
        userDTO.setPassword("password");

        user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setPassword("encodedPassword");
        user.setAuthorities(Set.of(Role.ROLE_USER));
    }

    @Test
    void testRegister_Success() {
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(userDTO.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO savedUserDTO = userService.register(userDTO);

        assertNotNull(savedUserDTO);
        assertEquals(userDTO.getEmail(), savedUserDTO.getEmail());
        assertEquals(userDTO.getName(), savedUserDTO.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.register(userDTO));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testGetUserById_Success() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTO foundUserDTO = userService.getUserById(user.getId().toString());

        assertNotNull(foundUserDTO);
        assertEquals(user.getId(), foundUserDTO.getId());
        assertEquals(user.getEmail(), foundUserDTO.getEmail());
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(user.getId().toString()));

        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUser(user.getId().toString());

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(user.getId().toString()));

        verify(userRepository, never()).delete(any(User.class));
    }
}
