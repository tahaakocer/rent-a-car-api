package com.yazilimxyz.rent_a_car.service;

import com.yazilimxyz.rent_a_car.dto.RentalDTO;
import com.yazilimxyz.rent_a_car.dto.UserDTO;
import com.yazilimxyz.rent_a_car.dto.VehicleDTO;
import com.yazilimxyz.rent_a_car.entity.Rental;
import com.yazilimxyz.rent_a_car.entity.Vehicle;
import com.yazilimxyz.rent_a_car.exception.RentalNotAvailableException;
import com.yazilimxyz.rent_a_car.exception.RentalNotFoundException;
import com.yazilimxyz.rent_a_car.repository.RentalRepository;
import com.yazilimxyz.rent_a_car.service.interfaces.IRentalService;
import com.yazilimxyz.rent_a_car.utils.mapper.RentalMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class RentalService implements IRentalService {
    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final VehicleService vehicleService;


    public RentalService(RentalRepository rentalRepository, UserService userService, VehicleService vehicleService) {
        this.rentalRepository = rentalRepository;
        this.userService = userService;
        this.vehicleService = vehicleService;
    }
    private boolean vehicleIsAvailable(RentalDTO rentalDTO, List<RentalDTO> existingRentals) {
        return existingRentals.stream()
                .noneMatch(existingRental ->
                        rentalDTO.getCheckInDate().isBefore(existingRental.getCheckOutDate()) &&
                                rentalDTO.getCheckOutDate().isAfter(existingRental.getCheckInDate())
                );
    }
    @Override
    public RentalDTO saveRental(Long vehicleId, UUID userId, RentalDTO rentalDTO) {
        VehicleDTO vehicleDTO = this.vehicleService.getVehicleById(String.valueOf(vehicleId));
        UserDTO userDTO = this.userService.getUserById(String.valueOf(userId));
        List<RentalDTO> existingRentals = vehicleDTO.getRentals();
        if(!vehicleIsAvailable(rentalDTO, existingRentals)) {
            throw new RentalNotAvailableException("Vehicle is not available for selected date range");
        }
        rentalDTO.setVehicle(vehicleDTO);
        rentalDTO.setUser(userDTO);
        String rentalConfirmationCode = UUID.randomUUID().toString();
        rentalDTO.setRentalConfirmationCode(rentalConfirmationCode);
        Rental rental = this.rentalRepository.save(RentalMapper.INSTANCE.rentalDtoToRental(rentalDTO));
        return RentalMapper.INSTANCE.rentalToRentalDto(rental);
    }

    @Override
    public RentalDTO findRentalByConfirmationCode(String confirmationCode) {
        Rental rental = this.rentalRepository.findByRentalConfirmationCode(confirmationCode);
        if(rental == null) {
            throw new RentalNotAvailableException("Rental not found for confirmation code: " + confirmationCode);
        }
        return RentalMapper.INSTANCE.rentalToRentalDto(rental);
    }

    @Override
    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = this.rentalRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<RentalDTO> rentalDTOs = new ArrayList<>();
        rentals.forEach(rental -> rentalDTOs.add(RentalMapper.INSTANCE.rentalToRentalDto(rental)));
        return rentalDTOs;
    }

    @Override
    public void deleteRental(Long rentalId) {
        Rental rental = this.rentalRepository.findById(rentalId).orElseThrow(() -> new RentalNotFoundException("Rental not found for id: " + rentalId));
        this.rentalRepository.delete(rental);
    }
}
