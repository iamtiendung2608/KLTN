package com.block_chain.KLTN.domain.order;

import java.time.OffsetDateTime;

public record OrderResponse(
    Long id,
    OrderStatus status,
    float totalWeight,
    float totalPrice,
    OffsetDateTime deliveryAt,
    DeliveryType deliveryType,
    OffsetDateTime createdAt
) {
    
}
