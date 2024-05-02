package com.block_chain.KLTN.domain.location_tag;

import com.block_chain.KLTN.common.repository.QueryDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationTagRepository extends JpaRepository<LocationTagEntity, Long>, QueryDslRepository<LocationTagEntity>,
        QuerydslPredicateExecutor<LocationTagEntity> {
}
