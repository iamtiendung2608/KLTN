package com.block_chain.KLTN.domain.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionQueryService transactionQueryService;
    private final TransactionService transactionService;

    @GetMapping("/{orderId}")
    public ResponseEntity<List<TransactionResponse>> getTransaction(@PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok(transactionQueryService.getTransaction(orderId));
    }
}
