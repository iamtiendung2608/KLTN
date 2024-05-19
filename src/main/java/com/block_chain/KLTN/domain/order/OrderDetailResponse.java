package com.block_chain.KLTN.domain.order;

import com.block_chain.KLTN.domain.order.order_item.OrderItemResponse;

import java.time.OffsetDateTime;
import java.util.List;

public record OrderDetailResponse(
        Long id,
        OrderStatus status,
        String note,
        Float totalWeight,
        Float totalPrice,
        OffsetDateTime deliveryAt,
        OffsetDateTime estimatedDeliveryAt,
        DeliveryType deliveryType,
        OffsetDateTime createdAt,
        PaidType paidType,
        List<OrderItemResponse> orderItems
) {
}
