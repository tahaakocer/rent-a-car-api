package com.yazilimxyz.rent_a_car.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "vehicles")
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Type is required")
    private String vehicleType;  // Sedan, SUV, Hatchback vb.

    @NotBlank(message = "Brand is required")
    private String brand;        // Araç markası (Örn: BMW, Mercedes)

    @NotBlank(message = "Model is required")
    private String model;        // Araç modeli (Örn: 320i, C-Class)

    @NotBlank(message = "rental price is required")
    private BigDecimal rentalPrice;  // Araç kiralama ücreti

    private String vehiclePhotoUrl;  // Araç fotoğrafı URL'si

    private String vehicleDescription; // Araç açıklaması

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Rental> rentals = new ArrayList<>();

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicleType='" + vehicleType + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", rentalPrice=" + rentalPrice +
                ", vehiclePhotoUrl='" + vehiclePhotoUrl + '\'' +
                ", vehicleDescription='" + vehicleDescription + '\'' +
                '}';
    }
}