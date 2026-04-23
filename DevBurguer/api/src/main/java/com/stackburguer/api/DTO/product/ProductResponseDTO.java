package com.stackburguer.api.DTO.product;

public record ProductResponseDTO (
    Long id,
    String name,
    Double price,
    String imageUrl,
    String url,
    String categoryId,
    boolean offer
) {}
