package com.block_chain.KLTN.domain.postOffices;

import com.block_chain.KLTN.common.OptionalBooleanBuilder;
import com.querydsl.core.types.Predicate;

public record PostOfficesSearchRequest(
        String keyword
) {
    public Predicate toPredicate() {
        QPostOfficesEntity qPostOfficesEntity = QPostOfficesEntity.postOfficesEntity;
        return new OptionalBooleanBuilder(qPostOfficesEntity.deleted.eq(false))
                .notBlankAnd(qPostOfficesEntity.name::containsIgnoreCase, keyword)
                .build();
    }
}
