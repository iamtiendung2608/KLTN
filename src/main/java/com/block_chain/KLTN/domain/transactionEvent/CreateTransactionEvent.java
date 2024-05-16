package com.block_chain.KLTN.domain.transactionEvent;

import com.block_chain.KLTN.domain.transaction.TransactionEntity;

public record CreateTransactionEvent(
    TransactionEntity oldTransaction,
    TransactionEntity newTransaction
) {
    
}
