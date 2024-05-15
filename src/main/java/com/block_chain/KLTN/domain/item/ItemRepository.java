package com.block_chain.KLTN.domain.item;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    @Query(value = """
            SELECT oi.item FROM OrderItemEntity oi
            WHERE oi.order.id = :orderId
            """)
    List<ItemEntity> findByOrderId(Long orderId);
}
