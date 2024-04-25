package com.block_chain.KLTN.domain.order;

import java.time.OffsetDateTime;

public record OrderUpdateRequest(
    OrderStatus status,
    OffsetDateTime deliveryAt,
    OffsetDateTime estimatedDeliveryAt
) {
    
}
