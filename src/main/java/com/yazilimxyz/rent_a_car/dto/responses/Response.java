package com.yazilimxyz.rent_a_car.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yazilimxyz.rent_a_car.dto.RentalDTO;
import com.yazilimxyz.rent_a_car.dto.UserDTO;
import com.yazilimxyz.rent_a_car.dto.VehicleDTO;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private int statusCode;
    private String message;
    private String token;
    private String role;
    private String expirationTime;
    private String bookingConfirmationCode;

    private UserDTO user;
    private VehicleDTO vehicle;
    private RentalDTO rental;
    private List<RentalDTO> rentals;
    private List<VehicleDTO> vehicles;
    private List<UserDTO> users;


}
