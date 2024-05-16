package com.block_chain.KLTN.domain.transaction;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    CREATED,
    RECEIVED,
    TRANSPORTING,
    TRANSPORTED,
    DELIVERING,
    DELIVERIED;
    // To-do: some status for exception
}
