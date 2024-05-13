package com.block_chain.KLTN.domain.employee;

import com.block_chain.KLTN.common.OptionalBooleanBuilder;
import com.querydsl.core.types.Predicate;

public record EmployeeSearchRequest(
        String keyword,
        Long locationTagId,
        Long postOfficeId
) {

    public Predicate toPredicate() {
        QEmployeeEntity qEmployee = QEmployeeEntity.employeeEntity;
        return new OptionalBooleanBuilder(qEmployee.id.gt(0L)) //modify default condition later
                .notNullAnd(qEmployee.locationTagId::eq, locationTagId)
                // .notNullAnd(qEmployee.postOfficeId::eq, postOfficeId)
                .notBlankAnd(qEmployee.name::containsIgnoreCase, keyword)
                .notBlankAnd(qEmployee.email::containsIgnoreCase, keyword)
                .build();
    }
}
