package com.block_chain.KLTN.domain.order.orderItem;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponse toResponse(OrderItemEntity orderItem);
    List<OrderItemResponse> toResponseList(List<OrderItemEntity> orderItems);
    // OrderItemEntity toEntity(OrderItemRequest orderItem);
}