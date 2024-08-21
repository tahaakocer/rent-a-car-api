package com.yazilimxyz.rent_a_car.service.interfaces;

import com.yazilimxyz.rent_a_car.dto.RentalDTO;
import java.util.List;
import java.util.UUID;

public interface IRentalService {
    RentalDTO saveRental(Long vehicleId, UUID userId, RentalDTO rentalDTO);
    RentalDTO findRentalByConfirmationCode(String confirmationCode);
    List<RentalDTO> getAllRentals();
    void deleteRental(Long rentalId);
}
