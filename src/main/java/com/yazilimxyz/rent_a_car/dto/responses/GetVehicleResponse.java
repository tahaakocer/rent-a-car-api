package com.yazilimxyz.rent_a_car.dto.responses;

import com.yazilimxyz.rent_a_car.dto.RentalDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetVehicleResponse {
    private Long id;
    private String vehicleType;
    private String brand;
    private String model;
    private BigDecimal rentalPrice;
    private String vehiclePhotoUrl;
    private String vehicleDescription;
    private List<RentalDTO> rentals;
}
