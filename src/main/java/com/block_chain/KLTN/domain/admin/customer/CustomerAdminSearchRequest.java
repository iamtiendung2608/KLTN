package com.block_chain.KLTN.domain.admin.customer;

import com.block_chain.KLTN.common.OptionalBooleanBuilder;
import com.block_chain.KLTN.domain.customer.QCustomerEntity;
import com.querydsl.core.types.Predicate;

public record CustomerAdminSearchRequest(
    String keyword,
    boolean deleted
) {
    public Predicate toPredicate() {
        QCustomerEntity qCustomerEntity = QCustomerEntity.customerEntity;
        return new OptionalBooleanBuilder(qCustomerEntity.id.gt(0L))
                .notNullAnd(qCustomerEntity.fullName::containsIgnoreCase, keyword)
                .notNullAnd(qCustomerEntity.isDeleted::eq, deleted).build();
    }
}
