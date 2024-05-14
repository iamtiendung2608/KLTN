package com.block_chain.KLTN.domain.transaction;

public record TransactionResponse(
    Long id,
    TransactionStatus status,
    String note
    //TODO: add more field later
) {
}
