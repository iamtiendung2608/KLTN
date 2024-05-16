package com.block_chain.KLTN.domain.item;

import java.util.List;

import org.mapstruct.Mapper;

import com.block_chain.KLTN.domain.order.orderItem.OrderItemRequest;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemResponse toResponse(ItemEntity item);
    ItemEntity toEntity(OrderItemRequest itemRequest);
    List<ItemEntity> toListEntity(List<OrderItemRequest> itemsRequests);
}
