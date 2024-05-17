package com.block_chain.KLTN.domain.transactionEvent;

import java.time.OffsetDateTime;
import java.util.List;

import com.block_chain.KLTN.domain.order.OrderStatus;
import com.block_chain.KLTN.util.AppUtil;



public record OrderEvent (
    Long order_id,
    String created_at,
    String created_by,
    Float totalWeight,
    Float totalPrice,
    String note,
    OrderStatus status,
    List<ItemAttributeEvent> items
){
    public OrderEvent withItems(List<ItemAttributeEvent> items){
        return new OrderEvent(order_id, created_at, created_by, totalWeight, totalPrice, note, status, items);
    }

    public OrderEvent covertOffsetDateTime(OffsetDateTime created_at){
        return new OrderEvent(order_id, AppUtil.dateFormatter(created_at), created_by, totalWeight, totalPrice, note, status, items);
    }
    
}
