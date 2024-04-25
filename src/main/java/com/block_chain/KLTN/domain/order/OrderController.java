package com.block_chain.KLTN.domain.order;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.block_chain.KLTN.security.UserPrincipal;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderQueryService orderQueryService;
    @GetMapping("/")
    public Page<OrderResponse> SearchOrder(OrderSearchRequest request, Pageable pageable) {
        return orderQueryService.searchOrder(request, pageable); //orderQueryService.searchOrder();
    }
    
    @PostMapping("")
    public OrderResponse createOrder(@Valid @RequestBody OrderCreateRequest orderRequest){
        return orderService.createOrder(orderRequest);
    }
}
