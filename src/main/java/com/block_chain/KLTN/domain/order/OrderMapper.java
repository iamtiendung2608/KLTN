package com.block_chain.KLTN.domain.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "orderItems", target = "items")
    OrderResponse toResponse(OrderEntity order);

    @Mapping(target = "sender", source = "senderObject")
    @Mapping(target = "receiver", source = "receiverObject")
    OrderDetailResponse toDetailResponse(OrderEntity order);
}
