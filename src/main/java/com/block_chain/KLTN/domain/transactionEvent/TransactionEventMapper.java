package com.block_chain.KLTN.domain.transactionEvent;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.block_chain.KLTN.domain.item.ItemEntity;
import com.block_chain.KLTN.domain.order.OrderEntity;

@Mapper(componentModel = "spring")
public interface TransactionEventMapper {

    @Mapping(target = "unitPrice", source = "item.price")
    ItemAttributeEvent toEvent(ItemEntity item, Integer quantity);
    
    @Mapping(target = "order_id", source="id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", source="order.organization.name")
    OrderEvent toEvent(OrderEntity order);
}
