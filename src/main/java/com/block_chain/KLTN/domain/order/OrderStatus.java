package com.block_chain.KLTN.domain.order;

import java.util.HashMap;
import java.util.Map;

import com.block_chain.KLTN.domain.transaction.TransactionStatus;

import lombok.Getter;

@Getter
public enum OrderStatus {
    DRAFT, CREATED, RECEIVED, TRANSPORTING, TRANSPORTED, DELIVERING, DELIVERED, CANCELED, REFUNDED;

    private TransactionStatus status;
    public static Map<TransactionStatus, OrderStatus> transactionStatusMap = new HashMap<TransactionStatus, OrderStatus>() {{
        put(TransactionStatus.CREATED, CREATED);
        put(TransactionStatus.RECEIVED, RECEIVED);
        put(TransactionStatus.TRANSPORTING, TRANSPORTING);
        put(TransactionStatus.TRANSPORTED, TRANSPORTED);
        put(TransactionStatus.DELIVERING, DELIVERING);
        put(TransactionStatus.DELIVERIED, DELIVERED);
    }};
    
    public static OrderStatus get(TransactionStatus status){
        return transactionStatusMap.get(status);
    };
}
