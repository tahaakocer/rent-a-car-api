package com.yazilimxyz.rent_a_car.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yazilimxyz.rent_a_car.dto.UserDTO;
import com.yazilimxyz.rent_a_car.dto.VehicleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;


import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddRentalResponse {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numOfDrivers;
    private String rentalConfirmationCode;
    private UUID userId;
    private Long vehicleId;

}
