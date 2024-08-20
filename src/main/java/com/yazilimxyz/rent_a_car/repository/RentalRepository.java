package com.yazilimxyz.rent_a_car.repository;

import com.yazilimxyz.rent_a_car.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findByVehicleId(Long id);
    List<Rental> findByRentalConfirmationCode(String confirmationCode );
    List<Rental> findByUserId(UUID id);
}
