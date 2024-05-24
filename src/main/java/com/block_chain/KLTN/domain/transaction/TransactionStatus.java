package com.block_chain.KLTN.domain.transaction;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    CREATED(0),
    RECEIVED(1),
    TRANSPORTING(2),
    TRANSPORTED(2),
    DELIVERING(3),
    DELIVERED(4);

    private int step;

    TransactionStatus(int step) {
        this.step = step;
    }
    // To-do: some status for exception
}
