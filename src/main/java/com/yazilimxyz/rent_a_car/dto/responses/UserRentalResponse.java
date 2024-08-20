package com.yazilimxyz.rent_a_car.dto.responses;

import com.yazilimxyz.rent_a_car.dto.RentalDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRentalResponse {

    private UUID id;
    private String email;
    private String name;
    private List<RentalDTO> rentals;
}
