package com.block_chain.KLTN.domain.wallet;

import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultWalletQueryService implements WalletQueryService {
    private final WalletRepository walletRepository;


    @Override
    public WalletEntity getWallet(WalletType type, long userId) {
        return walletRepository.findByTypeAndUserId(type, userId).orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "wallet"));
    }
}
