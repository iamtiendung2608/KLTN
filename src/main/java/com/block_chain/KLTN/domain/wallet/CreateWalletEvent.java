package com.block_chain.KLTN.domain.wallet;

public record CreateWalletEvent(
        Long code,
        WalletType type
) {
}
