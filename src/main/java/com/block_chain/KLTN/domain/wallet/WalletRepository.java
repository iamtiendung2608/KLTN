package com.block_chain.KLTN.domain.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, String> {
    Optional<WalletEntity> findByTypeAndCode(WalletType type, long code);
}
