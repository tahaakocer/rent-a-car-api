package com.yazilimxyz.rent_a_car.dto.requests;


import com.yazilimxyz.rent_a_car.dto.UserDTO;
import com.yazilimxyz.rent_a_car.dto.VehicleDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddRentalRequest {
    private UUID userId;
    private Long vehicleId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numOfDrivers;

}
