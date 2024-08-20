package com.yazilimxyz.rent_a_car.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RentalVehicleDTO {
    private Long id;
    private String vehicleType;
    private String brand;
    private String model;
    private BigDecimal rentalPrice;
    private String vehiclePhotoUrl;
    private String vehicleDescription;
}
