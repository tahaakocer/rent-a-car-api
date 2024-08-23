package com.yazilimxyz.rent_a_car.utils.mapper;

import com.yazilimxyz.rent_a_car.dto.RentalDTO;
import com.yazilimxyz.rent_a_car.dto.requests.AddRentalRequest;
import com.yazilimxyz.rent_a_car.dto.responses.AddRentalResponse;
import com.yazilimxyz.rent_a_car.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RentalMapper {

    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);
    RentalDTO rentalToRentalDto(Rental rental);
    Rental rentalDtoToRental(RentalDTO rentalDTO);
    RentalDTO rentalRequestToRentalDto(AddRentalRequest addRentalRequest);
    AddRentalResponse rentalDtoToAddRentalResponse(RentalDTO rentalDTO);
}
