package com.yazilimxyz.rent_a_car.service;

import com.yazilimxyz.rent_a_car.dto.VehicleDTO;
import com.yazilimxyz.rent_a_car.entity.Vehicle;
import com.yazilimxyz.rent_a_car.exception.VehicleNotFoundException;
import com.yazilimxyz.rent_a_car.repository.VehicleRepository;
import com.yazilimxyz.rent_a_car.service.interfaces.IVehicleService;
import com.yazilimxyz.rent_a_car.utils.mapper.VehicleMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Service
public class VehicleService implements IVehicleService {
    private final VehicleRepository vehicleRepository;
    private final AwsS3Service awsS3Service;

    public VehicleService(VehicleRepository vehicleRepository, AwsS3Service awsS3Service) {
        this.vehicleRepository = vehicleRepository;
        this.awsS3Service = awsS3Service;
    }

    @Override
    public VehicleDTO addVehicle(VehicleDTO vehicleDTO) {
        String imageUrl = this.awsS3Service.saveImage(vehicleDTO.getVehiclePhotoFile());
        vehicleDTO.setVehiclePhotoUrl(imageUrl);
        Vehicle vehicle = this.vehicleRepository.save(VehicleMapper.INSTANCE.vehicleDtoToVehicle(vehicleDTO));
        return VehicleMapper.INSTANCE.vehicleToVehicleDto(vehicle);
    }

    @Override
    public VehicleDTO getVehicleById(Long vehicleId) {
        Vehicle vehicle = this.vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new VehicleNotFoundException(vehicleId + " not found"));
        return VehicleMapper.INSTANCE.vehicleToVehicleDto(vehicle);
    }

    @Override
    public VehicleDTO updateVehicle(Long vehicleId, VehicleDTO vehicleDTO) {
        Vehicle vehicle = this.vehicleRepository.findById(vehicleId).orElseThrow(
                () -> new VehicleNotFoundException(vehicleId + " not found"));
        vehicle = VehicleMapper.INSTANCE.vehicleDtoToVehicle(vehicleDTO);
        vehicle = this.vehicleRepository.save(vehicle);
        return VehicleMapper.INSTANCE.vehicleToVehicleDto(vehicle);
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        List<Vehicle> vehicles = this.vehicleRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        vehicles.forEach(vehicle -> vehicleDTOList.add(VehicleMapper.INSTANCE.vehicleToVehicleDto(vehicle)));
        return vehicleDTOList;
    }

    @Override
    public List<VehicleDTO> getAvailableVehicles() {
        List<Vehicle> vehicles = this.vehicleRepository.getAllAvailableVehicles();
        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        vehicles.forEach(vehicle -> vehicleDTOList.add(VehicleMapper.INSTANCE.vehicleToVehicleDto(vehicle)));
        return vehicleDTOList;
    }

    @Override
    public List<String> getAllVehicleTypes() {
        return this.vehicleRepository.findDistinctVehicleTypes();
    }

    @Override
    public List<VehicleDTO> getAvailableVehiclesByDateAndType(LocalDate checkInDate, LocalDate checkOutDate, String vehicleType) {
        List<Vehicle> vehicles = this.vehicleRepository.findAvailableVehiclesByDatesAndTypes(checkInDate, checkOutDate, vehicleType);
        List<VehicleDTO> vehicleDTOList = new ArrayList<>();
        vehicles.forEach(vehicle -> vehicleDTOList.add(VehicleMapper.INSTANCE.vehicleToVehicleDto(vehicle)));
        return vehicleDTOList;
    }

    @Override
    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = this.vehicleRepository.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException(vehicleId + " not found"));
        this.vehicleRepository.delete(vehicle);
    }
}
