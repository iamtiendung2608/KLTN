package com.block_chain.KLTN.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/search-assigned")
    @PreAuthorize("hasAuthority('super_admin') or hasAuthority('employee')")
    public Page<OrderResponse> searchOrderAssigned(OrderSearchRequest request, Pageable pageable) {
        return orderQueryService.searchOrderAssigned(request, pageable);
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

    
    @GetMapping("/track/{id}")
    public OrderLocationResponse getOrderLocation(@PathVariable("id") Long id) {
        return orderQueryService.getOrderLocation(id);
    }
}
