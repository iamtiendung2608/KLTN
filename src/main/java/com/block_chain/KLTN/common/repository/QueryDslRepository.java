package com.block_chain.KLTN.common.repository;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryDslRepository<T> {

    Page<T> findAll(JPQLQuery<T> jpqlQuery, Pageable pageable);
}