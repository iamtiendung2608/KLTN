package com.block_chain.KLTN.domain.order;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.block_chain.KLTN.domain.item.ItemEntity;
import com.block_chain.KLTN.domain.item.ItemMapper;
import com.block_chain.KLTN.domain.item.ItemRepository;
import com.block_chain.KLTN.domain.order.order_item.OrderItemEntity;
import com.block_chain.KLTN.domain.order.order_item.OrderItemRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultOrderService implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderCreateRequest order) {
        List<ItemEntity> items = itemMapper.toListEntity(order.items());
        List<OrderItemEntity> orderItems = new ArrayList<>();

        float totalPrice = 0; 
        float totalWeight = 0;
        OffsetDateTime estimatedDeliveryAt = OffsetDateTime.now().plusDays(7L);
        
        OrderEntity orderEntity = OrderEntity.builder()
        .status(order.status())
        .estimatedDeliveryAt(estimatedDeliveryAt)
        .deliveryType(order.deliveryType())
        .note(order.note())
        .paidType(order.paidType())
        .build();

        for (OrderItemRequest item : order.items()) {
            totalPrice += item.price() * item.quantity();
            totalWeight += item.weight() * item.quantity();
            OrderItemEntity orderItem = OrderItemEntity.builder()
                .order(orderEntity)
                .item(itemMapper.toEntity(item))
                .quantity(item.quantity())
                .build();
            orderItems.add(orderItem);
        }

        orderEntity.setTotalPrice(totalPrice);
        orderEntity.setTotalWeight(totalWeight);
        orderEntity.setOrderItems(orderItems);

        orderRepository.save(orderEntity);
        itemRepository.saveAllAndFlush(items);

        return orderMapper.toResponse(orderEntity);
    }
    
}
