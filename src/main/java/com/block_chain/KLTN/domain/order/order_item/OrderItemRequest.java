package com.block_chain.KLTN.domain.order.order_item;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.block_chain.KLTN.domain.item.ItemCategory;

public record OrderItemRequest(
    @NotNull String name,
    @PositiveOrZero int price,
    @Positive float weight,
    @NotNull ItemCategory itemCategory,
    @Positive int quantity
) {
    
}
