package com.block_chain.KLTN.domain.transfer_object;

import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultTransferObjectQueryService implements TransferObjectQueryService {
    private final TransferObjectRepository transferObjectRepository;
    private final TransferObjectMapper transferObjectMapper;
    @Override
    public TransferObjectResponse getTransferObject(Long id) {
        TransferObjectEntity transferObjectEntity = transferObjectRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "Customer"));
        return transferObjectMapper.toResponse(transferObjectEntity);
    }

    @Override
    public Page<TransferObjectResponse> searchTransferObject(TransferObjectSearchRequest request, Pageable pageable) {
        return transferObjectRepository.findAll(pageable).map(transferObjectMapper::toResponse);
    }
}
