package com.block_chain.KLTN.domain.transactionEvent;

import com.block_chain.KLTN.domain.transaction.TransactionEntity;
import com.block_chain.KLTN.domain.transaction.TransactionStatus;

public record CreateTransactionEvent(
    TransactionEntity oldTransaction,
    TransactionEntity newTransaction
) {
    
}
