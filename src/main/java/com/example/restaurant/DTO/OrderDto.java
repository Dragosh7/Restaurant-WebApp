package com.example.restaurant.DTO;

import com.example.restaurant.entity.Food;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Builder
public record OrderDto(
    List<Food> command,
    Double totalPrice,
    String status,
    LocalDate date){
}
