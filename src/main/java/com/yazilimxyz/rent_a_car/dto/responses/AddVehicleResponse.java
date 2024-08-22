package com.yazilimxyz.rent_a_car.dto.responses;

import com.yazilimxyz.rent_a_car.dto.RentalDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddVehicleResponse {
    private Long id;
    private String vehicleType;
    private String brand;
    private String model;
    private BigDecimal rentalPrice;
    private String vehiclePhotoUrl;
    private String vehicleDescription;
}
