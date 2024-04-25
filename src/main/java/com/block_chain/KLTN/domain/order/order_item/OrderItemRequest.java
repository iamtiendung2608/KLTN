package com.block_chain.KLTN.domain.order.order_item;

import com.block_chain.KLTN.domain.item.ItemCategory;

public record OrderItemRequest(
    Long id,
    String name,
    int weight,
    int price,
    int length,
    int height,
    int width,
    ItemCategory itemCategory,
    int quantity
) {
    
}
