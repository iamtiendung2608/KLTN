package com.block_chain.KLTN.domain.order;

import java.util.List;

import com.block_chain.KLTN.domain.order.order_item.OrderItemRequest;

public record OrderCreateRequest(
    List<OrderItemRequest> items,
    DeliveryType deliveryType,
    String note,
    PaidType paidType,
    OrderStatus status
) {
    
}
