package com.block_chain.KLTN.domain.item;

public record ItemResponse(
    Long id,
    String name,
    int weight,
    int price,
    int length,
    int height,
    int width,
    ItemCategory itemCategory
) {
    
}
