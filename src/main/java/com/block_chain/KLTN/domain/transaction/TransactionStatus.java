package com.block_chain.KLTN.domain.transaction;

import com.block_chain.KLTN.domain.order.OrderStatus;

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
