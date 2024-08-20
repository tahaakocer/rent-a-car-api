package com.yazilimxyz.rent_a_car.utils.mapper;

import com.yazilimxyz.rent_a_car.dto.UserDTO;
import com.yazilimxyz.rent_a_car.dto.requests.RegisterRequest;
import com.yazilimxyz.rent_a_car.dto.responses.UserRentalResponse;
import com.yazilimxyz.rent_a_car.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDto(User user);

    UserDTO registerRequestToUserDto(RegisterRequest registerRequest);

    UserRentalResponse userToUserRentalResponse(User user);
    User userDtoToUser(UserDTO userDTO);



}
