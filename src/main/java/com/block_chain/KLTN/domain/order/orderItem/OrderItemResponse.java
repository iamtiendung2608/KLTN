package com.block_chain.KLTN.domain.order.orderItem;

import com.block_chain.KLTN.domain.item.ItemResponse;

public record OrderItemResponse(
    ItemResponse item,
    int quantity
) {
    
}
