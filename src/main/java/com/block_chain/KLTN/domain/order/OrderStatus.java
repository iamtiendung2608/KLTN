package com.block_chain.KLTN.domain.order;

import com.block_chain.KLTN.domain.transaction.TransactionStatus;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum OrderStatus {
    DRAFT(0), 
    CREATED(1), 
    RECEIVED(2), 
    TRANSPORTING(2), 
    TRANSPORTED(2), 
    DELIVERING(3), 
    DELIVERED(4), 
    CANCELED(5), 
    REFUNDED(5), 
    CANCELLED(5);

    private TransactionStatus status;
    private int step;
    public static Map<TransactionStatus, OrderStatus> transactionStatusMap = new HashMap<TransactionStatus, OrderStatus>() {{
        put(TransactionStatus.CREATED, CREATED);
        put(TransactionStatus.RECEIVED, RECEIVED);
        put(TransactionStatus.TRANSPORTING, TRANSPORTING);
        put(TransactionStatus.TRANSPORTED, TRANSPORTED);
        put(TransactionStatus.DELIVERING, DELIVERING);
        put(TransactionStatus.DELIVERED, DELIVERED);
    }};
    
    public static OrderStatus get(TransactionStatus status){
        return transactionStatusMap.get(status);
    };

    OrderStatus(int step){
        this.step = step;
    }
}
