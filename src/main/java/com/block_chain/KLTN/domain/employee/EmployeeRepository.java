package com.block_chain.KLTN.domain.employee;

import com.block_chain.KLTN.common.repository.QueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, QueryDslRepository<EmployeeEntity>, QuerydslPredicateExecutor<EmployeeEntity> {
    Optional<EmployeeEntity> findByEmail(String email);
}
