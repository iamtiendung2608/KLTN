package com.block_chain.KLTN.domain.postOffices;

import com.block_chain.KLTN.domain.location_tag.LocationTagEntity;
import com.block_chain.KLTN.domain.location_tag.LocationTagRepository;
import com.block_chain.KLTN.domain.wallet.CreateWalletEvent;
import com.block_chain.KLTN.domain.wallet.WalletType;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultPostOfficesService implements PostOfficesService { 
    private final PostOfficesRepository postOfficesRepository;
    private final LocationTagRepository locationTagRepository;

    private final PostOfficesMapper postOfficesMapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    /*
     * Khúc này vẽ bùa trừ tà cmnl
     */
    @Override
    @Transactional
    public boolean checkWallet() {
        List<PostOfficesEntity> postOffices = postOfficesRepository.findAll();
        
        for (PostOfficesEntity postOffice : postOffices) {
            if (postOffice.getWalletAddress() == null) {
                applicationEventPublisher.publishEvent(new CreateWalletEvent(postOffice.getId(), WalletType.POST_OFFICES));
            }
        }

        return true;
    }
    @Override
    public CreatePostOfficesResponse create(PostOfficesRequest request) {
        if (postOfficesRepository.existsByCode(request.code())) {
            throw new BusinessException(ErrorMessage.RESOURCE_EXISTS, "code post office exists");
        }
        if (postOfficesRepository.existsByName(request.name())){
            throw new BusinessException(ErrorMessage.RESOURCE_EXISTS, "name post office exists");
        }
        LocationTagEntity location = locationTagRepository.findById(request.locationTagId())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "location tag"));
        PostOfficesEntity entity = postOfficesMapper.toEntity(request);

        entity.setLocationTag(location);
        postOfficesRepository.save(entity);
        applicationEventPublisher.publishEvent(new CreateWalletEvent(entity.getId(), WalletType.POST_OFFICES));
        return new CreatePostOfficesResponse(entity.getId());
    }

    @Override
    public PostOfficesResponse update(Long id, UpdatePostOfficesRequest request) {
        PostOfficesEntity postOffice = postOfficesRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "post office"));
      
        if (request.code().isEmpty()) {
            request = request.updateCode(postOffice.getCode());
        }
        if (!(postOffice.getCode().equals(request.code())) 
            && postOfficesRepository.existsByCode(request.code())) {
            throw new BusinessException(ErrorMessage.RESOURCE_EXISTS, "code post office exists");
        }
        if (!postOffice.getName().equals(request.name()) &&
            postOfficesRepository.existsByName(request.name())){
            throw new BusinessException(ErrorMessage.RESOURCE_EXISTS, "name post office exists");
        }

        postOfficesMapper.updateEntity(postOffice, request);
        postOfficesRepository.save(postOffice);
        return postOfficesMapper.toResponse(postOffice);
    }
    
    @Override
    public void delete(Long id) {
        PostOfficesEntity postOffice = postOfficesRepository.findById(id)
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "post office"));
        
        postOffice.setDeleted(true);
        postOfficesRepository.save(postOffice);
    }
    
}
