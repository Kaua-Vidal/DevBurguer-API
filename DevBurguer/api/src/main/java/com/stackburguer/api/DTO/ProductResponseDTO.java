package com.stackburguer.api.DTO;

public record ProductResponseDTO (
    Long id,
    String name,
    Double price,
    String imageUrl,
    String categoryId,
    String url,
    boolean offer
) {}
