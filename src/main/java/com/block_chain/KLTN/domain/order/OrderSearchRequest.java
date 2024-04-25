package com.block_chain.KLTN.domain.order;

import java.time.OffsetDateTime;

import com.block_chain.KLTN.common.OptionalBooleanBuilder;
import com.querydsl.core.types.Predicate;

public record OrderSearchRequest(
    String keyword, //status, paid_type
    Long customer_id
    // Todo: search order by post office and search in range of date
    // OffsetDateTime date_start
    // OffsetDateTime date_end
    // Long post_office_id
) {
    public Predicate toPredicate() {
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        return new OptionalBooleanBuilder(qOrderEntity.id.lt(1L)) //modify default condition later //huh ?
                .notNullAnd(qOrderEntity.organizationId::eq, customer_id)
                .notNullAnd(qOrderEntity.status.stringValue()::containsIgnoreCase, keyword)
                .notNullAnd(qOrderEntity.paidType.stringValue()::containsIgnoreCase, keyword)
                .build();
    }
}
