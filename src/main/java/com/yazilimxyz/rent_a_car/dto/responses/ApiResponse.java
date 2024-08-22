package com.yazilimxyz.rent_a_car.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

}