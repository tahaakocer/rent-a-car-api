package com.yazilimxyz.rent_a_car.controller;

import com.yazilimxyz.rent_a_car.dto.VehicleDTO;
import com.yazilimxyz.rent_a_car.dto.requests.AddVehicleRequest;
import com.yazilimxyz.rent_a_car.dto.responses.ApiResponse;
import com.yazilimxyz.rent_a_car.dto.responses.GetVehicleResponse;
import com.yazilimxyz.rent_a_car.service.interfaces.IVehicleService;
import com.yazilimxyz.rent_a_car.utils.mapper.VehicleMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final IVehicleService vehicleService;

    public VehicleController(IVehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<?>> addVehicle(AddVehicleRequest addVehicleRequest, MultipartFile image) {
        addVehicleRequest.setVehiclePhotoFile(image);
        VehicleDTO vehicleDTO = this.vehicleService.addVehicle(
                VehicleMapper.INSTANCE.addVehicleRequestToVehicleDto(addVehicleRequest)
        );
        return ResponseEntity.ok(ApiResponse.builder()
                        .status(200)
                        .data(VehicleMapper.INSTANCE.vehicleDtotoAddVehicleResponse(vehicleDTO))
                        .message("Vehicle added successfully")
                .build());
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<?>> getAllVehicles() {
        List<GetVehicleResponse> responses = new ArrayList<>();
        this.vehicleService.getAllVehicles().forEach(
                vehicleDTO -> responses.add(VehicleMapper.INSTANCE.vehicleDtotoGetVehicleResponse(vehicleDTO))
        );
        return ResponseEntity.ok(ApiResponse.builder()
                .data(responses)
                .status(200)
                .message("Vehicles get successfully")
                .build());
    }
    @GetMapping("/types")
    public List<String> getVehicleTypes() {
        return this.vehicleService.getAllVehicleTypes();
    }

    @GetMapping("/get-by-id/{vehicleId}")
    public ResponseEntity<ApiResponse<?>> getVehicleById(@PathVariable("vehicleId") Long vehicleId) {
        VehicleDTO vehicleDTO = this.vehicleService.getVehicleById(vehicleId);
        return ResponseEntity.ok(ApiResponse.builder()
                .data(VehicleMapper.INSTANCE.vehicleDtotoGetVehicleResponse(vehicleDTO))
                .status(200)
                .message("Vehicle get successfully")
                .build());
    }

    @GetMapping("/get-available-vehicles")
    public ResponseEntity<ApiResponse<?>> getAvailableVehicles() {
        List<GetVehicleResponse> responses = new ArrayList<>();
        this.vehicleService.getAvailableVehicles().forEach(vehicle ->
                responses.add(VehicleMapper.INSTANCE.vehicleDtotoGetVehicleResponse(vehicle))
        );
        return ResponseEntity.ok(ApiResponse.builder()
                .data(responses)
                .status(200)
                .message("Available vehicles get successfully")
                .build());
    }

    @GetMapping("/get-available-vehicles-by-date-and-type")
    public ResponseEntity<ApiResponse<?>> getAvailableVehiclesByDateAndType(
            @RequestParam("checkInDate") LocalDate checkInDate,
            @RequestParam("checkOutDate") LocalDate checkOutDate,
            @RequestParam("vehicleType") String vehicleType
    ){
        List<GetVehicleResponse> responses = new ArrayList<>();
        this.vehicleService.getAvailableVehiclesByDateAndType(checkInDate,checkOutDate,vehicleType)
                .forEach(vehicle -> responses.add(VehicleMapper.INSTANCE.vehicleDtotoGetVehicleResponse(vehicle)));
        return ResponseEntity.ok(ApiResponse.builder()
                .data(responses)
                .status(200)
                .message("Available vehicles get successfully")
                .build());
    }
    @PutMapping("/update/{vehicleId}")
    public ResponseEntity<ApiResponse<?>> updateVehicle(@PathVariable("vehicleId") Long vehicleId,
                                                        AddVehicleRequest addVehicleRequest) {
        VehicleDTO vehicleDTO = VehicleMapper.INSTANCE.addVehicleRequestToVehicleDto(addVehicleRequest);
        vehicleDTO.setId(vehicleId);
        GetVehicleResponse getVehicleResponse = VehicleMapper.INSTANCE.vehicleDtotoGetVehicleResponse(
                this.vehicleService.updateVehicle(vehicleId, vehicleDTO));
        return ResponseEntity.ok(ApiResponse.builder()
                        .data(getVehicleResponse)
                        .status(200)
                        .message("Vehicle updated successfully")
                .build());
    }
    @DeleteMapping("/delete/{vehicleId}")
    public ResponseEntity<ApiResponse<?>> deleteVehicle(@PathVariable("vehicleId") Long vehicleId) {
        this.vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.ok(ApiResponse.builder()
                        .status(200)
                        .message("Vehicle deleted successfully")
                .build());
    }
}
