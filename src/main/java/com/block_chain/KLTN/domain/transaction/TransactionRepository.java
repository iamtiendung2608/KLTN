package com.block_chain.KLTN.domain.transaction;

import com.block_chain.KLTN.common.repository.QueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;


public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>, QueryDslRepository<TransactionEntity>,
        QuerydslPredicateExecutor<TransactionEntity> {

        @Query(value = "select * from transaction t where t.order_id = :orderId ORDER BY id DESC LIMIT 1",
                nativeQuery = true)
        TransactionEntity findLastTransaction(Long orderId);

        List<TransactionEntity> findAllByOrderId(Long orderId);
}
