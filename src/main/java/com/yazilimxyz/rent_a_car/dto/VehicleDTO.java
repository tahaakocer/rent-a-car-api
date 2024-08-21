package com.yazilimxyz.rent_a_car.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDTO {

    private Long id;
    private String vehicleType;
    private String brand;
    private String model;
    private BigDecimal rentalPrice;
    private MultipartFile vehiclePhotoFile;
    private String vehiclePhotoUrl;
    private String vehicleDescription;
    private List<RentalDTO> rentals;
}

