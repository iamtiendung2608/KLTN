package com.block_chain.KLTN.domain.transactionEvent;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.block_chain.KLTN.domain.item.ItemEntity;
import com.block_chain.KLTN.domain.item.ItemRepository;
import com.block_chain.KLTN.domain.order.OrderEntity;
import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.order.order_item.OrderItemRepository;
import com.block_chain.KLTN.domain.organization.OrganizationEntity;
import com.block_chain.KLTN.domain.organization.OrganizationRepository;
import com.block_chain.KLTN.domain.post_offices.PostOfficesEntity;
import com.block_chain.KLTN.domain.post_offices.PostOfficesRepository;
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
    private final OrderItemRepository orderItemRepository;
    private final OrganizationRepository organizationRepository;
    private final PostOfficesRepository postOfficesRepository;

    private final TransactionEventMapper transactionEventMapper;

    private final CreateTransactionProducer createTransactionProducer;

    @Override
    @Transactional
    public void createTransactionEvent(TransactionEntity oldTransaction, TransactionEntity newTransaction) {
        OrderEntity order = orderRepository.findById(newTransaction.getOrderId())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Order"));
        List<ItemAttributeEvent> items = orderItemRepository.findByOrderId(order.getId())
                .stream().map(item -> transactionEventMapper.toEvent(item.getItem(), item.getQuantity())).toList();
        
        OrderEvent orderEvent = transactionEventMapper.toEvent(order);
        orderEvent = orderEvent.withItems(items);
        orderEvent = orderEvent.covertOffsetDateTime(order.getCreatedAt());

        String senderAddress = null;
        String receiverAddress = null;
        String receiverName = null;

        switch (newTransaction.getStatus()) {
            case CREATED:{
                // sender: user - receiver: user
                OrganizationEntity organization = organizationRepository.findById(order.getOrganizationId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Organization"));
            
                senderAddress = organization.getWalletAddress();
                receiverAddress = organization.getWalletAddress();
                receiverName = organization.getName();
                break;
            }
            case RECEIVED:{
                // sender: user - receiver: postOffice
                OrganizationEntity organization = organizationRepository.findById(order.getOrganizationId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Organization"));
            
                PostOfficesEntity postOfficesEntity = postOfficesRepository.findById(newTransaction.getPostOfficeId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "PostOffice"));
                
                senderAddress = organization.getWalletAddress();
                receiverAddress = postOfficesEntity.getWalletAddress();
                receiverName = postOfficesEntity.getName();
                break;
            }
            case TRANSPORTING:{
                // sender: postOffice - receiver: postOffice
                PostOfficesEntity oldPostOffices = postOfficesRepository.findById(oldTransaction.getPostOfficeId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "sender PostOffice"));
                
                PostOfficesEntity newPostOffices = postOfficesRepository.findById(newTransaction.getPostOfficeId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "receiver PostOffice"));
                
                senderAddress = oldPostOffices.getWalletAddress();
                receiverAddress = newPostOffices.getWalletAddress();
                receiverName = newPostOffices.getName();
                break;
            }
            case TRANSPORTED:{
                // sender: postOffice - receiver: same postOffice
                PostOfficesEntity postOffices = postOfficesRepository.findById(newTransaction.getPostOfficeId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "PostOffice"));

                senderAddress = postOffices.getWalletAddress();
                receiverAddress = postOffices.getWalletAddress();
                receiverName = postOffices.getName();
                break;
            }
            case DELIVERING:
            case DELIVERIED:{
                // sender: postOffice - receiver: END_USER
                PostOfficesEntity postOffices = postOfficesRepository.findById(newTransaction.getPostOfficeId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "PostOffice"));
                
                senderAddress = postOffices.getWalletAddress();
                receiverAddress = "END_USER";
                receiverName = "END_USER";
                break;
            }
            default:
                throw new BusinessException(ErrorMessage.INVALID_REQUEST_PARAMETER, "Transaction status");
        }
        
        TransactionEventEntity transaction_event = TransactionEventEntity.builder()
            .transaction_id(newTransaction.getId())
            .senderAddress(senderAddress)
            .receiverAddress(receiverAddress)
            .receiverName(receiverName)
            .orderEvent(orderEvent)
            .status(newTransaction.getStatus())
            .createAt(AppUtil.dateFormatter(newTransaction.getCreatedAt()))
            .build();
        createTransactionProducer.sendMessage(transaction_event);
    }
}
