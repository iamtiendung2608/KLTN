package com.block_chain.KLTN.domain.wallet;

public interface WalletQueryService {
    WalletEntity getWallet(WalletType type, long userId);
}
