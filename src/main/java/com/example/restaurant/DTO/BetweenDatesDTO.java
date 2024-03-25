package com.example.restaurant.DTO;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BetweenDatesDTO (
    LocalDate startDate,
    LocalDate endDate){}

