package com.block_chain.KLTN.domain.item;

public record ItemResponse(
    Long id,
    String name,
    int price,
    float weight,
    ItemCategory itemCategory
) {
    
}
