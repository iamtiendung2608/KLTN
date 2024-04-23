package com.block_chain.KLTN.domain.transfer_object;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transfer-object")
@RequiredArgsConstructor
public class TransferObjectController {

    private final TransferObjectService transferObjectService;
    private final TransferObjectQueryService transferObjectQueryService;

    @PostMapping("")
    public CreateTransferObjectResponse create(@Valid @RequestBody TransferObjectRequest request) {
        return transferObjectService.create(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @Valid @RequestBody TransferObjectRequest request) {
        transferObjectService.update(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public TransferObjectResponse getTransferObject(@PathVariable("id") Long id) {
        return transferObjectQueryService.getTransferObject(id);
    }

    @GetMapping("")
    public Page<TransferObjectResponse> searchTransferObject(TransferObjectSearchRequest request, Pageable pageable) {
        return transferObjectQueryService.searchTransferObject(request, pageable);
    }
}
