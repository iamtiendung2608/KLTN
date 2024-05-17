package com.block_chain.KLTN.domain.order.order_item;

import com.block_chain.KLTN.domain.item.ItemCategory;

public record OrderItemRequest(
    String name,
    int price,
    float weight,
    ItemCategory itemCategory,
    int quantity
) {
    
}
