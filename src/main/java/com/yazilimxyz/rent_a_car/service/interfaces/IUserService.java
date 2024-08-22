package com.yazilimxyz.rent_a_car.service.interfaces;

import com.yazilimxyz.rent_a_car.dto.UserDTO;
import com.yazilimxyz.rent_a_car.dto.requests.LoginRequest;
import com.yazilimxyz.rent_a_car.dto.requests.RegisterRequest;
import com.yazilimxyz.rent_a_car.dto.responses.LoginResponse;
import java.util.List;

public interface IUserService {

    UserDTO register(UserDTO userDTO);

    LoginResponse login(UserDTO userDTO);
    List<UserDTO> getAllUsers();

 //   Response getUserRentalHistory(String userId);
    void deleteUser(String userId);
    UserDTO getUserById(String userId);
    UserDTO findUserByEmail(String email);
}
