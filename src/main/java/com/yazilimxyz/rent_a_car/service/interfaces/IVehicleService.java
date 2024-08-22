package com.yazilimxyz.rent_a_car.service.interfaces;

import com.yazilimxyz.rent_a_car.dto.VehicleDTO;

import java.time.LocalDate;
import java.util.List;


public interface IVehicleService {

    VehicleDTO addVehicle(VehicleDTO vehicleDTO);
    VehicleDTO getVehicleById(Long vehicleId);
    VehicleDTO updateVehicle(Long vehicleId, VehicleDTO vehicleDTO);
    List<VehicleDTO> getAllVehicles();
    List<VehicleDTO> getAvailableVehicles();
    List<String> getAllVehicleTypes();
    List<VehicleDTO> getAvailableVehiclesByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String vehicleType);
    void deleteVehicle(Long vehicleId);
}
