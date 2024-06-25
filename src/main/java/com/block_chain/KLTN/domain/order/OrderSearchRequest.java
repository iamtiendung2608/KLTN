package com.block_chain.KLTN.domain.order;

import com.block_chain.KLTN.common.OptionalBooleanBuilder;
import com.querydsl.core.types.Predicate;

public record OrderSearchRequest(
    Long customerId,
    OrderStatus status,
    DeliveryType deliveryType,
    PaidType paidType,
    String keyword
) {
    public Predicate toPredicate(Long organizationId) {
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        return new OptionalBooleanBuilder(qOrderEntity.organization.id.eq(organizationId))
                .notNullAnd(qOrderEntity.status::eq, status)
                .notNullAnd(qOrderEntity.deliveryType::eq, deliveryType)
                .notNullAnd(qOrderEntity.paidType::eq, paidType)
                .notBlankAnd(qOrderEntity.status.stringValue()::containsIgnoreCase, keyword)
                .build();
    }

    public Predicate employeeToPredicte(Long employeeId) {
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        
        return new OptionalBooleanBuilder(qOrderEntity.employeeId.isNull().and(qOrderEntity.status.ne(OrderStatus.DELIVERED)))
            .notNullAnd(qOrderEntity.status::eq, status)
            .notNullAnd(qOrderEntity.deliveryType::eq, deliveryType)
            .notNullAnd(qOrderEntity.paidType::eq, paidType)
            .build();
    }

    public Predicate orderAssigned(Long employeeId){
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        return new OptionalBooleanBuilder(qOrderEntity.employee.id.eq(employeeId))
                .notNullAnd(qOrderEntity.status::eq, status)
                .notNullAnd(qOrderEntity.deliveryType::eq, deliveryType)
                .notNullAnd(qOrderEntity.paidType::eq, paidType)
                .build();
    }

    public Predicate allOrder(){
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        return new OptionalBooleanBuilder(qOrderEntity.id.gt(0))
            .notNullAnd(qOrderEntity.status::eq, status)
            .notNullAnd(qOrderEntity.deliveryType::eq, deliveryType)
            .notNullAnd(qOrderEntity.paidType::eq, paidType)
            .build();
    }
}
