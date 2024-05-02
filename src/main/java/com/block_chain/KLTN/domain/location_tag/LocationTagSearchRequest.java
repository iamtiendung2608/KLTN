package com.block_chain.KLTN.domain.location_tag;

import com.block_chain.KLTN.common.OptionalBooleanBuilder;
import com.querydsl.core.types.Predicate;

public record LocationTagSearchRequest(
        String keyword
) {
    public Predicate toPredicate() {
        QLocationTagEntity qLocationTag = QLocationTagEntity.locationTagEntity;
        return new OptionalBooleanBuilder(qLocationTag.id.lt(1L))
                .notNullOr(qLocationTag.district::containsIgnoreCase, keyword)
                .notNullOr(qLocationTag.province::containsIgnoreCase, keyword)
                .notNullOr(qLocationTag.ward::containsIgnoreCase, keyword)
                .build();
    }
}
