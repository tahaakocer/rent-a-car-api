package com.yazilimxyz.rent_a_car.controller;

import com.yazilimxyz.rent_a_car.dto.UserDTO;
import com.yazilimxyz.rent_a_car.dto.requests.LoginRequest;
import com.yazilimxyz.rent_a_car.dto.requests.RegisterRequest;
import com.yazilimxyz.rent_a_car.dto.responses.ApiResponse;
import com.yazilimxyz.rent_a_car.dto.responses.LoginResponse;
import com.yazilimxyz.rent_a_car.dto.responses.RegisterResponse;
import com.yazilimxyz.rent_a_car.service.interfaces.IUserService;
import com.yazilimxyz.rent_a_car.utils.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest registerRequest){
        UserDTO userDTO = this.userService.register(UserMapper.INSTANCE.registerRequestToUserDto(registerRequest));
        RegisterResponse response = UserMapper.INSTANCE.userDtoToRegisterResponse(userDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(200)
                .message("registered successfully")
                .data(response)
                .build());

    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        UserDTO userDTO = UserMapper.INSTANCE.loginRequestToUserDto(loginRequest);
        LoginResponse loginResponse = this.userService.login(userDTO);
        return ResponseEntity.ok(loginResponse);
    }
}
