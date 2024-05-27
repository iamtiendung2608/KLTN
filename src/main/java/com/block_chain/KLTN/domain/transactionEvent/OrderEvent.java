package com.block_chain.KLTN.domain.transactionEvent;

import java.time.OffsetDateTime;
import java.util.List;

import com.block_chain.KLTN.domain.order.OrderStatus;
import com.block_chain.KLTN.util.AppUtil;



public record OrderEvent (
    Long order_id,
    String createdAt,
    String createdBy,
    Float totalWeight,
    Float totalPrice,
    Float subTotal,
    Float feePaid,
    String note,
    OrderStatus status,
    List<ItemAttributeEvent> items
){
    public OrderEvent withItems(List<ItemAttributeEvent> items){
        return new OrderEvent(order_id, createdAt, createdBy, totalWeight, totalPrice, feePaid, feePaid, note, status, items);
    }

    public OrderEvent covertOffsetDateTime(OffsetDateTime createdAt){
        return new OrderEvent(order_id, AppUtil.dateFormatter(createdAt), createdBy, totalWeight, totalPrice, subTotal, feePaid, note, status, items);
    }
    
}
