package com.yazilimxyz.rent_a_car.controller;

import com.yazilimxyz.rent_a_car.dto.RentalDTO;
import com.yazilimxyz.rent_a_car.dto.requests.AddRentalRequest;
import com.yazilimxyz.rent_a_car.dto.responses.AddRentalResponse;
import com.yazilimxyz.rent_a_car.dto.responses.ApiResponse;
import com.yazilimxyz.rent_a_car.service.interfaces.IRentalService;
import com.yazilimxyz.rent_a_car.utils.mapper.RentalMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/rentals")
public class RentalController {
    private final IRentalService rentalService;

    public RentalController(IRentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<?> > addRental(@RequestBody AddRentalRequest addRentalRequest) {
        RentalDTO rentalDTO = RentalMapper.INSTANCE.rentalRequestToRentalDto(addRentalRequest);
        RentalDTO savedRentalDTO = this.rentalService.saveRental(addRentalRequest.getVehicleId(), addRentalRequest.getUserId(), rentalDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                        .status(200)
                        .data(RentalMapper.INSTANCE.rentalDtoToAddRentalResponse(savedRentalDTO))
                        .message("Rental added successfully")
                .build()
        );

    }
    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllRentals() {
        List< AddRentalResponse> responses = new ArrayList<>();
        this.rentalService.getAllRentals().forEach(rentals -> responses.add(RentalMapper.INSTANCE.rentalDtoToAddRentalResponse(rentals)));
        return ResponseEntity.ok(ApiResponse.builder()
                        .status(200)
                        .data(responses)
                        .message("Rentals get successfully")
                .build());
    }
    @GetMapping("get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<ApiResponse<?>> getRentalByConfirmationCode(@PathVariable String confirmationCode) {
        RentalDTO rentalDTO = this.rentalService.findRentalByConfirmationCode(confirmationCode);

        return ResponseEntity.ok(ApiResponse.builder()
                        .status(200)
                        .data(RentalMapper.INSTANCE.rentalDtoToAddRentalResponse(rentalDTO))
                        .message("Rental get confirm code successfully")
                .build());
    }

    @DeleteMapping("/delete/{rentalId}")
    public ResponseEntity<ApiResponse<?>> deleteRentalById(@PathVariable("rentalId") Long rentalId) {

        this.rentalService.deleteRental(rentalId);
        return ResponseEntity.ok(ApiResponse.builder()
                        .status(200)
                        .message("Rental deleted successfully")
                .build());
    }

}
