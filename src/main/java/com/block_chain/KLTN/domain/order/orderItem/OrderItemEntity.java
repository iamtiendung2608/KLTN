package com.block_chain.KLTN.domain.order.orderItem;

import javax.persistence.*;

import com.block_chain.KLTN.domain.item.ItemEntity;
import com.block_chain.KLTN.domain.order.OrderEntity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="order_item")
public class OrderItemEntity {
    @EmbeddedId
    private OrderItemKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("order_id")
    @JoinColumn(name="order_id")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("item_id")
    @JoinColumn(name="item_id")
    private ItemEntity item;

    private int quantity; //quantity of item in order
}
