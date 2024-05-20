package com.block_chain.KLTN.domain.order;

import com.block_chain.KLTN.domain.order.order_item.OrderItemResponse;
import com.block_chain.KLTN.domain.transfer_object.TransferObjectResponse;

import java.time.OffsetDateTime;
import java.util.List;

public record OrderDetailResponse(
        Long id,
        OrderStatus status,
        String note,
        Float totalWeight,
        Float totalPrice,
        Float subTotal,
        Float feePaid,
        OffsetDateTime deliveryAt,
        OffsetDateTime estimatedDeliveryAt,
        DeliveryType deliveryType,
        OffsetDateTime createdAt,
        PaidType paidType,
        Integer step,
        List<OrderItemResponse> orderItems,
        TransferObjectResponse receiver,
        TransferObjectResponse sender
) {
}
