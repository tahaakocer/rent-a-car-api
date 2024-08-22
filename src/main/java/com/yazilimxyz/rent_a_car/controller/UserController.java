package com.yazilimxyz.rent_a_car.controller;

import com.yazilimxyz.rent_a_car.dto.responses.ApiResponse;
import com.yazilimxyz.rent_a_car.dto.responses.GetUserResponse;
import com.yazilimxyz.rent_a_car.service.UserService;
import com.yazilimxyz.rent_a_car.utils.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllUsers() {
        List<GetUserResponse> responses = new ArrayList<>();
        this.userService.getAllUsers().forEach(
                userDTO -> responses.add(UserMapper.INSTANCE.userDtoToGetUserResponse(userDTO))
        );
        return ResponseEntity.ok(ApiResponse.builder()
                        .status(200)
                        .data(responses)
                        .message("users get successfully")
                .build());
    }

    @GetMapping("/get-by-id/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable("userId") String userId) {
        GetUserResponse userResponse = UserMapper.INSTANCE.userDtoToGetUserResponse(
                this.userService.getUserById(userId));
        return ResponseEntity.ok(ApiResponse.builder()
                .status(200)
                .data(userResponse)
                .message("user get successfully")
                .build());
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteUserById(@PathVariable("userId") String userId) {
       this.userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.builder()
                .status(200)
                .message("user deleted successfully")
                .build());
    }

    @GetMapping("/get-logged-in-user")
    public ResponseEntity<ApiResponse<?>> getLoggedInUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        GetUserResponse userResponse = UserMapper.INSTANCE.userDtoToGetUserResponse(
                this.userService.findUserByEmail(authentication.getName())
        );
        return ResponseEntity.ok(ApiResponse.builder()
                .status(200)
                .data(userResponse)
                .message("user get successfully")
                .build());
    }

    @GetMapping("/get-rentals/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserRentals(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(ApiResponse.builder()
                        .status(200)
                        .data(this.userService.getUserById(userId).getRentals())
                        .message("user rental get successfully")
                .build());
    }
 }


