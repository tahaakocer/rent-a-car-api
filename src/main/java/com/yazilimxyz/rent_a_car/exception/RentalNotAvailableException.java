package com.yazilimxyz.rent_a_car.exception;

public class RentalNotAvailableException extends RuntimeException{

    public RentalNotAvailableException(String message) {
        super(message);
    }
}
