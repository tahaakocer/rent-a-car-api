package com.yazilimxyz.rent_a_car.service;

import com.yazilimxyz.rent_a_car.dto.RentalDTO;
import com.yazilimxyz.rent_a_car.dto.UserDTO;
import com.yazilimxyz.rent_a_car.dto.VehicleDTO;
import com.yazilimxyz.rent_a_car.entity.Rental;
import com.yazilimxyz.rent_a_car.exception.RentalNotAvailableException;
import com.yazilimxyz.rent_a_car.exception.RentalNotFoundException;
import com.yazilimxyz.rent_a_car.repository.RentalRepository;
import com.yazilimxyz.rent_a_car.service.interfaces.IRentalService;
import com.yazilimxyz.rent_a_car.service.interfaces.IUserService;
import com.yazilimxyz.rent_a_car.service.interfaces.IVehicleService;
import com.yazilimxyz.rent_a_car.utils.mapper.RentalMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class RentalService implements IRentalService {
    private final RentalRepository rentalRepository;
    private final IUserService userService;
    private final IVehicleService vehicleService;


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
        VehicleDTO vehicleDTO = this.vehicleService.getVehicleById(vehicleId);
        UserDTO userDTO = this.userService.getUserById(String.valueOf(userId));
        List<RentalDTO> existingRentals = vehicleDTO.getRentals();
        if(!vehicleIsAvailable(rentalDTO, existingRentals)) {
            throw new RentalNotAvailableException("Vehicle is not available for selected date range");
        }
        rentalDTO.setVehicle(vehicleDTO);
        rentalDTO.setUser(userDTO);
        String rentalConfirmationCode = UUID.randomUUID().toString();
        rentalDTO.setRentalConfirmationCode(rentalConfirmationCode);
        Rental rental = RentalMapper.INSTANCE.rentalDtoToRental(rentalDTO);
        Rental savedRental = this.rentalRepository.save(rental);
        return RentalMapper.INSTANCE.rentalToRentalDto(savedRental);
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
        rentalDTOs.forEach(rental -> {
            rental.setVehicleId(rental.getVehicleId());
            rental.setUserId(rental.getUserId());
        });

        return rentalDTOs;
    }

    @Override
    public void deleteRental(Long rentalId) {
        Rental rental = this.rentalRepository.findById(rentalId).orElseThrow(
                () -> new RentalNotFoundException("Rental not found for id: " + rentalId));
        this.rentalRepository.delete(rental);
    }
}
