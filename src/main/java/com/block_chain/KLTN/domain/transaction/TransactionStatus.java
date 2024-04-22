package com.block_chain.KLTN.domain.transaction;

import lombok.Getter;

@Getter
public enum TransactionStatus {
    WAITING,
    SENT,
    DELIVERY,
    RECEIVED
}
