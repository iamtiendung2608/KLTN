package com.block_chain.KLTN.common.repository;

import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class QueryDslRepositoryImpl<T> implements QueryDslRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<T> findAll(JPQLQuery<T> jpqlQuery, Pageable pageable) {
        Assert.notNull(jpqlQuery, "JPQLQuery must not be null!");
        Assert.notNull(pageable, "Pageable must not be null!");

        // Count query
        final JPQLQuery<?> countQuery = jpqlQuery;
        Querydsl querydsl = new Querydsl(entityManager, new PathBuilderFactory().create(jpqlQuery.getType()));

        // Apply pagination
        JPQLQuery<T> query = querydsl.applyPagination(pageable, jpqlQuery);

        // Run query
        return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchCount);
    }
}

