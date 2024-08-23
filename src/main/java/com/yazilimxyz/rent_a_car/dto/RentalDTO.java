package com.yazilimxyz.rent_a_car.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class RentalDTO {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numOfDrivers;
    private String rentalConfirmationCode;
    @JsonIgnore
    private UserDTO user;
    @JsonIgnore
    private VehicleDTO vehicle;
    private UUID userId;
    private Long vehicleId;
}
