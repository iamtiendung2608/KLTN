package com.block_chain.KLTN.domain.transaction;

public record UpdateTransactionRequest(
    Long id,
    TransactionStatus status
) { }
