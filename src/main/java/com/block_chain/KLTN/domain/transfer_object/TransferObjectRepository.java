package com.block_chain.KLTN.domain.transfer_object;

import com.block_chain.KLTN.common.repository.QueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TransferObjectRepository extends JpaRepository<TransferObjectEntity, Long>, QueryDslRepository<TransferObjectEntity>, QuerydslPredicateExecutor<TransferObjectEntity> {
}
