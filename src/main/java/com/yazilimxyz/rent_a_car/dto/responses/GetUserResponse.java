package com.yazilimxyz.rent_a_car.dto.responses;

import com.yazilimxyz.rent_a_car.dto.RentalDTO;
import com.yazilimxyz.rent_a_car.entity.enums.Role;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetUserResponse {
    private UUID id;
    private String email;
    private String name;
    private String phoneNumber;
    private Set<Role> authorities;
    private List<RentalDTO> rentals;
}
