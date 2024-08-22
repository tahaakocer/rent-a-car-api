package com.yazilimxyz.rent_a_car.dto.responses;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

}