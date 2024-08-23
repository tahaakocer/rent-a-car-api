package com.yazilimxyz.rent_a_car.utils.mapper;

import com.yazilimxyz.rent_a_car.dto.VehicleDTO;
import com.yazilimxyz.rent_a_car.dto.requests.AddVehicleRequest;
import com.yazilimxyz.rent_a_car.dto.responses.AddVehicleResponse;
import com.yazilimxyz.rent_a_car.dto.responses.GetVehicleResponse;
import com.yazilimxyz.rent_a_car.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VehicleMapper {
    VehicleMapper INSTANCE = Mappers.getMapper(VehicleMapper.class);
    VehicleDTO vehicleToVehicleDto(Vehicle vehicle);
    Vehicle vehicleDtoToVehicle(VehicleDTO vehicleDTO);
    VehicleDTO addVehicleRequestToVehicleDto(AddVehicleRequest addVehicleRequest);
    AddVehicleResponse vehicleDtotoAddVehicleResponse(VehicleDTO vehicleDTO);
    GetVehicleResponse vehicleDtotoGetVehicleResponse(VehicleDTO vehicleDTO);
    VehicleDTO GetVehicleResponseToVehicleDto(GetVehicleResponse getVehicleResponse);
}
