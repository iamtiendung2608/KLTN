package com.block_chain.KLTN.domain.customer;

import com.block_chain.KLTN.common.repository.QueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>,
        QueryDslRepository<CustomerEntity>, QuerydslPredicateExecutor<CustomerEntity> {
}
