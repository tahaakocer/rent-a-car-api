package com.yazilimxyz.rent_a_car.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yazilimxyz.rent_a_car.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private UUID id;
    private String email;
    private String name;
    private String phoneNumber;
    private String password;
    private Set<Role> authorities;
    private List<RentalDTO> rentals;
}
