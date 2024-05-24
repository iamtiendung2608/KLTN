package com.block_chain.KLTN.domain.admin.order;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.block_chain.KLTN.domain.order.*;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class OrderAdminController {
    private final OrderQueryService orderQueryService;

    @GetMapping("")
    public Page<OrderResponse> getMethodName(@Valid @RequestBody OrderSearchRequest request, Pageable pageable) {
        return orderQueryService.searchAllOrder(request, pageable);
    }
    
}
