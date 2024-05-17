package com.block_chain.KLTN.domain.postOffices;

import com.block_chain.KLTN.common.repository.QueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostOfficesRepository extends JpaRepository<PostOfficesEntity, Long>,
        QueryDslRepository<PostOfficesEntity>, QuerydslPredicateExecutor<PostOfficesEntity> {

    boolean existsByName(String name);
    boolean existsByCode(String code);
}
