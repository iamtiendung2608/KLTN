package com.block_chain.KLTN.domain.post_offices;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.block_chain.KLTN.domain.wallet.CreateWalletEvent;
import com.block_chain.KLTN.domain.wallet.WalletType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultPostOfficesService implements PostOfficesService {
    private final PostOfficesRepository postOfficesRepository;
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
    
}
