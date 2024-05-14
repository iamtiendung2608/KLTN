package com.block_chain.KLTN.domain.wallet;

public interface WalletService {
    void createWallet(CreateWalletEvent event);
    String getWalletAddress(WalletType type, Integer code);
}
