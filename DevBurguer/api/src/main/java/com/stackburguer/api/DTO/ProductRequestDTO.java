package com.stackburguer.api.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequestDTO (
        @NotBlank(message = "O nome do produto é obrigatório")
        String name,

        @NotNull(message = "O preço não pode ser nulo")
        @Positive(message = "O proceço deve ser um valor positivo")
        Double price,

        @NotBlank(message = "A categoria é obrigatória")
        String categoryId
) {
}
