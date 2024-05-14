package com.block_chain.KLTN.domain.transaction;

import com.querydsl.core.types.Predicate;

public record TransactionSearchRequest(
        TransactionStatus status,
        Long orderId,
        Long postOfficeId,
        Long employeeId
) {
    public Predicate toPredicate() {
        return null;
    }
}
