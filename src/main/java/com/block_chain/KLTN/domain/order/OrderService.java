package com.block_chain.KLTN.domain.order;

public interface OrderService{
    OrderResponse createOrder(OrderCreateRequest orderReq);
    OrderResponse updateOrderStatus(Long id, OrderUpdateStatusRequest orderReq);
}
