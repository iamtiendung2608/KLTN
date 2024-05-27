package com.block_chain.KLTN.domain.customer;

import com.block_chain.KLTN.common.OptionalBooleanBuilder;
import com.querydsl.core.types.Predicate;

public record CustomerSearchRequest(
        String keyword,
        boolean deleted
) {
    public Predicate toPredicate(long organizationId) {
        QCustomerEntity qCustomerEntity = QCustomerEntity.customerEntity;
        return new OptionalBooleanBuilder(qCustomerEntity.organization.id.eq(organizationId))
                .notNullAnd(qCustomerEntity.fullName::containsIgnoreCase, keyword)
                .notNullAnd(qCustomerEntity.address::containsIgnoreCase, keyword)
                .notNullAnd(qCustomerEntity.email::containsIgnoreCase, keyword)
                .notNullAnd(qCustomerEntity.isDeleted::eq, deleted).build();
    }
}
