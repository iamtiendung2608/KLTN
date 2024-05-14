package com.block_chain.KLTN.domain.order.order_item;

import com.block_chain.KLTN.domain.item.ItemCategory;
import com.block_chain.KLTN.domain.item.ItemResponse;

public record OrderItemResponse(
    ItemResponse item,
    int quantity
) {
    
}
