package com.block_chain.KLTN.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    DRAFT, CREATED, RECEIVED, TRANSPORTING, TRANSPORTED, DELIVERING, DELIVERED, CANCELED, REFUNDED
}
