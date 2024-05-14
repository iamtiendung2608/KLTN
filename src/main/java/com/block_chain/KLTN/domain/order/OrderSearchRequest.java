package com.block_chain.KLTN.domain.order;

import com.block_chain.KLTN.common.OptionalBooleanBuilder;
import com.querydsl.core.types.Predicate;

public record OrderSearchRequest(
    Long customer_id,
    OrderStatus status,
    DeliveryType deliveryType,
    PaidType paidType,
    Long organizationId
) {
    public Predicate toPredicate() {
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        return new OptionalBooleanBuilder(qOrderEntity.organization.id.eq(organizationId))
                .notNullAnd(qOrderEntity.status::eq, status)
                .notNullAnd(qOrderEntity.deliveryType::eq, deliveryType)
                .notNullAnd(qOrderEntity.paidType::eq, paidType)
                .build();
    }
}
