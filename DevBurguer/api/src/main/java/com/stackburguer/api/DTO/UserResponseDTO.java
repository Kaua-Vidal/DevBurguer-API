package com.stackburguer.api.DTO;

import jakarta.persistence.Id;

import java.util.UUID;

public record UserResponseDTO (
        UUID id,
        String name,
        String email,
        boolean admin
) {
}
