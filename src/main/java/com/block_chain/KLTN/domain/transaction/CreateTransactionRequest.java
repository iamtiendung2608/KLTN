package com.block_chain.KLTN.domain.transaction;

import javax.validation.constraints.NotNull;


public record CreateTransactionRequest(
        @NotNull TransactionStatus status,
        String note,
        @NotNull Long orderId,
        Long postOfficeId
) {
}
