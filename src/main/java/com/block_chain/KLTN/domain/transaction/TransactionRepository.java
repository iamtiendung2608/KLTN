package com.block_chain.KLTN.domain.transaction;

import com.block_chain.KLTN.common.repository.QueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>, QueryDslRepository<TransactionEntity>,
        QuerydslPredicateExecutor<TransactionEntity> {
}
