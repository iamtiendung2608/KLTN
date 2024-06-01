package com.block_chain.KLTN.domain.order;

import java.time.OffsetDateTime;

public record OrderResponse(
    Long id,
    OrderStatus status,
    float totalWeight,
    float totalPrice,
    float feePaid,
    float subTotal,
    OffsetDateTime deliveryAt,
    DeliveryType deliveryType,
    OffsetDateTime createdAt
) {
    
}
