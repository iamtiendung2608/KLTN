package com.block_chain.KLTN.domain.order.order_item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, OrderItemKey>{
    
}
