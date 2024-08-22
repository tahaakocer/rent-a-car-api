package com.yazilimxyz.rent_a_car.utils.mapper;

import com.yazilimxyz.rent_a_car.dto.UserDTO;
import com.yazilimxyz.rent_a_car.dto.requests.LoginRequest;
import com.yazilimxyz.rent_a_car.dto.requests.RegisterRequest;
import com.yazilimxyz.rent_a_car.dto.responses.GetUserResponse;
import com.yazilimxyz.rent_a_car.dto.responses.RegisterResponse;
import com.yazilimxyz.rent_a_car.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDto(User user);
    UserDTO registerRequestToUserDto(RegisterRequest registerRequest);
    UserDTO loginRequestToUserDto(LoginRequest loginRequest);
    User userDtoToUser(UserDTO userDTO);
    @Mapping(target = "password", ignore = true)
    RegisterResponse userDtoToRegisterResponse(UserDTO userDTO);
    GetUserResponse userDtoToGetUserResponse(UserDTO userDTO);



}
