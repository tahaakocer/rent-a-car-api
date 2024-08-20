package com.yazilimxyz.rent_a_car.utils.mapper;

import com.yazilimxyz.rent_a_car.dto.RentalDTO;
import com.yazilimxyz.rent_a_car.entity.Rental;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;

@Mapper
public interface RentalMapper {

    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

    RentalDTO rentalToRentalDto(Rental rental);

    Rental rentalDtoToRental(RentalDTO rentalDTO);
}
