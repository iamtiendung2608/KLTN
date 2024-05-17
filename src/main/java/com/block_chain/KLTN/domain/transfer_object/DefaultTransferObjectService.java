package com.block_chain.KLTN.domain.transfer_object;

import com.block_chain.KLTN.domain.customer.CustomerEntity;
import com.block_chain.KLTN.domain.customer.CustomerRepository;
import com.block_chain.KLTN.domain.postOffices.PostOfficesEntity;
import com.block_chain.KLTN.domain.postOffices.PostOfficesRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import java.util.Objects;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultTransferObjectService implements TransferObjectService {
    private final CustomerRepository customerRepository;
    private final TransferObjectRepository transferObjectRepository;
    private final PostOfficesRepository postOfficesRepository;
    @Override
    @Transactional
    public CreateTransferObjectResponse create(TransferObjectRequest request) {
        CustomerEntity customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Customer"));

        TransferObjectEntity entity = TransferObjectEntity.builder()
                .atOfficeFlg(request.atOfficeFlg())
                .receiveShift(request.receiveShift())
                .customerId(customer.getId())
                .actionDate(request.actionDate())
                .build();

        if (request.atOfficeFlg() && !Objects.isNull(request.postOfficeId())) {
            PostOfficesEntity postOffice = postOfficesRepository.findById(request.postOfficeId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Post Office"));

            entity.setPostOfficeId(postOffice.getId());
        }
        transferObjectRepository.save(entity);
        return new CreateTransferObjectResponse(entity.getId());
    }

    @Override
    @Transactional
    public void update(Long id, TransferObjectRequest request) {
        TransferObjectEntity transferObject = transferObjectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "TransferObject"));

        transferObject.setAtOfficeFlg(request.atOfficeFlg());
        transferObject.setReceiveShift(request.receiveShift());

        if (!transferObject.getPostOfficeId().equals(request.postOfficeId())) {
            PostOfficesEntity postOffice = postOfficesRepository.findById(request.postOfficeId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Post Office"));

            transferObject.setPostOfficeId(postOffice.getId());
        }

        if (!transferObject.getPostOfficeId().equals(request.postOfficeId())) {
            PostOfficesEntity postOffice = postOfficesRepository.findById(request.postOfficeId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Post Office"));

            transferObject.setPostOfficeId(postOffice.getId());
        }

        if (!transferObject.getCustomerId().equals(request.customerId())) {
            CustomerEntity customer = customerRepository.findById(request.customerId())
                    .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Customer"));

            transferObject.setCustomerId(customer.getId());
        }
        transferObjectRepository.save(transferObject);
    }
}
