package com.block_chain.KLTN.domain.order;

import com.block_chain.KLTN.common.repository.QueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends 
    JpaRepository<OrderEntity, Long>,
    QueryDslRepository<OrderEntity>, 
    QuerydslPredicateExecutor<OrderEntity> {

    Long countByCreatedAtAfter(OffsetDateTime created);
}
