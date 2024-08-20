package com.yazilimxyz.rent_a_car.repository;

import com.yazilimxyz.rent_a_car.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("SELECT DISTINCT v.vehicleType FROM Vehicle v")
    List<String> findDistinctVehicleTypes();

    @Query("""
        SELECT v FROM Vehicle v 
        WHERE v.vehicleType = :vehicleType 
        AND v.id NOT IN (
            SELECT r.vehicle.id FROM Rental r 
            WHERE (r.checkInDate <= :checkOutDate AND r.checkOutDate >= :checkInDate)
        )
    """)
    List<Vehicle> findAvailableVehiclesByDatesAndTypes(@Param("checkInDate") LocalDate checkInDate,
                                                       @Param("checkOutDate") LocalDate checkOutDate,
                                                       @Param("vehicleType") String vehicleType);

    @Query("SELECT v FROM Vehicle v WHERE v.id NOT IN (SELECT r.vehicle.id FROM Rental r)")
    List<Vehicle> getAllAvailableVehicles();
}
