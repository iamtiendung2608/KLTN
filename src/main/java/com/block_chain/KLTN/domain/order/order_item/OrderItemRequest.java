package com.block_chain.KLTN.domain.order.order_item;

import com.block_chain.KLTN.domain.item.ItemCategory;

public record OrderItemRequest(
    String name,
    int price,
    float weight,
    float length,
    float height,
    float width,
    ItemCategory itemCategory,
    
    int quantity
) {
    
}
