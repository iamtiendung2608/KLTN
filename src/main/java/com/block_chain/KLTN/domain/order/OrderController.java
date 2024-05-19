package com.block_chain.KLTN.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderQueryService orderQueryService;
    @GetMapping("")
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

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable("id") Long id) {
        return orderQueryService.getOrderDetail(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
