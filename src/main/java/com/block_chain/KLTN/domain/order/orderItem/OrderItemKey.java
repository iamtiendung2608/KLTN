package com.block_chain.KLTN.domain.order.orderItem;

import java.io.Serializable;

import javax.persistence.Column;
import lombok.*;

@Data
@AllArgsConstructor @NoArgsConstructor 
@EqualsAndHashCode
public class OrderItemKey implements Serializable {
    @Column(name="order_id")
    private Long orderId;

    @Column(name="item_id")
    private Long itemId;   
}
