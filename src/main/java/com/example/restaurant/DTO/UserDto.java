package com.example.restaurant.DTO;

import lombok.Builder;

@Builder
public record UserDto(
        String username,
        String emailAddress,
        Boolean active,
        String role) {
}
