package com.block_chain.KLTN.domain.wallet;

public record CreateWalletEvent(
        String code,
        WalletType type
) {
}
