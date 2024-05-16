package com.block_chain.KLTN.domain.order;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



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
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest orderRequest){
        return orderService.createOrder(orderRequest);
    }

    @PutMapping("/{id}")
    public OrderResponse updateStatus(@PathVariable Long id, @RequestBody OrderUpdateStatusRequest req) {
        
        return orderService.updateOrderStatus(id, req);
    }
}
