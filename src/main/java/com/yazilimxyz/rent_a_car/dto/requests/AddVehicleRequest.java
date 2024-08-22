package com.yazilimxyz.rent_a_car.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class    AddVehicleRequest {
    MultipartFile vehiclePhotoFile;

    @NotBlank(message = "Vehicle type is required")
    String vehicleType;

    @NotBlank(message = "Rental price is required")
    BigDecimal rentalPrice;

    String vehicleDescription;

    @NotBlank(message = "Brand is required")
    String brand;

    @NotBlank(message = "Model is required")
    String model;
}
