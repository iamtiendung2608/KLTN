package com.block_chain.KLTN.domain.order;

import com.block_chain.KLTN.domain.order.order_item.OrderItemRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record OrderCreateRequest(
    @NotEmpty List<OrderItemRequest> items,
    @NotNull DeliveryType deliveryType,
    String note,
    @NotNull PaidType paidType,
    @NotNull OrderStatus status
) {
    
}
