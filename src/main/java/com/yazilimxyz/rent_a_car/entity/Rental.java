package com.yazilimxyz.rent_a_car.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "checkin date is required")
    private LocalDate checkInDate;

    @Future(message = "check out date must be in the future")
    private LocalDate checkOutDate;

    @Min(value = 1, message = "number of drivers must be at least 1")
    private Integer numOfDrivers;

    private String rentalConfirmationCode;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", pickUpDate=" + checkInDate +
                ", dropOffDate=" + checkOutDate +
                ", numOfDrivers=" + numOfDrivers +
                ", rentalConfirmationCode='" + rentalConfirmationCode + '\'' +
                '}';
    }
}