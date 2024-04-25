package com.block_chain.KLTN.domain.order.order_item;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderItemKey implements Serializable {
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "item_id")
    private Long itemId;
}
