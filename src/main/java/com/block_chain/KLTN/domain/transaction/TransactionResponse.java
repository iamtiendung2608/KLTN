package com.block_chain.KLTN.domain.transaction;

import com.block_chain.KLTN.domain.transfer_object.TransferObjectResponse;

import java.time.OffsetDateTime;

public record TransactionResponse(
    Long id,
    TransactionStatus status,
    String note,
    TransferObjectResponse.PostOfficesShortResponse postOffice,
    EmployeeShortResponse employee,
    OffsetDateTime createdAt
) {
    public record EmployeeShortResponse(
        Long id,
        String name,
        String address,
        String email
    ) {}
}
