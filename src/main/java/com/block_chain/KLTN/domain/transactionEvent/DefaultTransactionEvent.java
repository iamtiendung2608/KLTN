package com.block_chain.KLTN.domain.transactionEvent;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.block_chain.KLTN.domain.item.ItemEntity;
import com.block_chain.KLTN.domain.order.OrderEntity;
import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.organization.OrganizationEntity;
import com.block_chain.KLTN.domain.organization.OrganizationRepository;
import com.block_chain.KLTN.domain.post_offices.PostOfficesEntity;
import com.block_chain.KLTN.domain.transaction.TransactionEntity;
import com.block_chain.KLTN.domain.transaction.TransactionStatus;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import com.block_chain.KLTN.publiser.CreateTransactionProducer;
import com.block_chain.KLTN.util.AppUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultTransactionEvent implements TransactionEventService {

    private final OrderRepository orderRepository;
    private final OrganizationRepository organizationRepository;

    private final TransactionEventMapper transactionEventMapper;

    private final CreateTransactionProducer createTransactionProducer;

    @Override
    @Transactional
    public void createTransactionEvent(TransactionEntity oldTransaction, TransactionEntity newTransaction) {
        PostOfficesEntity postOffice = newTransaction.getPostOffice();
        OrderEntity order = orderRepository.findById(newTransaction.getOrderId())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Order"));
        List<ItemAttributeEvent> items = order.getOrderItems()
                .stream().map(item -> transactionEventMapper.toEvent(item.getItem(), item.getQuantity())).toList();
        
        OrderEvent orderEvent = transactionEventMapper.toEvent(order);
        orderEvent.withItems(items);
        orderEvent.covertOffsetDateTime(order.getCreatedAt());
        
        if (newTransaction.getStatus() == TransactionStatus.CREATED) {
            OrganizationEntity organization = organizationRepository.findById(order.getOrganizationId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Organization"));
            
            log.info("Create Transaction Event: {}", newTransaction);
            TransactionEventEntity transaction_event = TransactionEventEntity.builder()
                .transaction_id(newTransaction.getId())
                .senderAddress(organization.getWalletAddress())
                .receiverAddress(null)
                .receiverName(null)
                .orderEvent(orderEvent)
                .status(newTransaction.getStatus())
                .createAt(AppUtil.dateFormatter(newTransaction.getCreatedAt()))
                .build();
            
            createTransactionProducer.sendMessage(transaction_event);
        }
    }
    
}
