package com.block_chain.KLTN.domain.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionQueryService transactionQueryService;
    private final TransactionService transactionService;

    @GetMapping("")
    public Page<TransactionResponse> searchTransaction(TransactionSearchRequest request, Pageable pageable) {
        return transactionQueryService.searchTransaction(request, pageable);
    }

    @GetMapping("/{id}")
    public TransactionDetailResponse getTransaction(@PathVariable("id") Long id) {
        return transactionQueryService.getTransactionDetail(id);
    }

    @PostMapping("")
    public CreateTransactionResponse createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        return transactionService.createTransaction(request);
    }
}
