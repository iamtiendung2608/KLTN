package com.block_chain.KLTN.domain.transactionEvent;

import com.block_chain.KLTN.domain.transaction.TransactionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class TransactionEventEntity {
    private String senderAddress;
    private String receiverAddress;
    private String receiverName;
    private Long transactionId;
    private OrderEvent orderEvent;
    private TransactionStatus status;
    private String createAt;
}
