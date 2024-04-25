package com.block_chain.KLTN.domain.order;

import java.time.OffsetDateTime;
import java.util.List;

import com.block_chain.KLTN.domain.item.ItemEntity;
import com.block_chain.KLTN.domain.item.ItemResponse;

public record OrderResponse(
    Long id,
    OrderStatus status,
    float totalWeight,
    float totalPrice,
    OffsetDateTime deliveryAt,
    OffsetDateTime estimatedDeliveryAt,
    DeliveryType deliveryType,
    String note,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt,
    Long organizationId,
    List<ItemResponse> items
) {
    
}
