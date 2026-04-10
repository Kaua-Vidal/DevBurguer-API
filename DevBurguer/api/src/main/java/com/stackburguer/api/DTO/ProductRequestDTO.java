package com.stackburguer.api.DTO;

public record ProductRequestDTO (
        String name,
        Double price,
        String categoryId
) {
}
