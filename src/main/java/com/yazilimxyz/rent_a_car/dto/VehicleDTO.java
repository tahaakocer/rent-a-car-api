package com.yazilimxyz.rent_a_car.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @JsonIgnore
    private List<RentalDTO> rentals;
}

