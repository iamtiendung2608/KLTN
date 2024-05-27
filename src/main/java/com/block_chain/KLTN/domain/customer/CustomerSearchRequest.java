package com.block_chain.KLTN.domain.customer;

import org.hibernate.event.spi.PreDeleteEvent;

import com.block_chain.KLTN.common.OptionalBooleanBuilder;
import com.querydsl.core.types.Predicate;

public record CustomerSearchRequest(
        String keyword,
        boolean deleted
) {
    public Predicate toPredicate(long organizationId) {
        QCustomerEntity qCustomerEntity = QCustomerEntity.customerEntity;
        return new OptionalBooleanBuilder(qCustomerEntity.organizationId.eq(organizationId))
                .notEmptyAndMultipleOr(keyword, qCustomerEntity.fullName::contains, 
                    qCustomerEntity.email::containsIgnoreCase, 
                    qCustomerEntity.address::containsIgnoreCase)
                .notNullAnd(qCustomerEntity.isDeleted::eq, deleted).build();
    }
}
