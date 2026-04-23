package com.stackburguer.api.DTO.order;

import com.stackburguer.api.models.order.ProductItem;
import com.stackburguer.api.models.order.UserSummary;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        String id,
        UserSummary user,
        List<ProductItem> products,
        String status,
        LocalDateTime createdAt,
        String paymentUrl
) {

}
