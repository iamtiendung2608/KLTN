package com.block_chain.KLTN.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.block_chain.KLTN.common.repository.QueryDslRepository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>,
    QueryDslRepository<UserEntity>, QuerydslPredicateExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);

}
