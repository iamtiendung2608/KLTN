package com.block_chain.KLTN.domain.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toResponse(OrderEntity order);
}
