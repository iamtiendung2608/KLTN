package com.block_chain.KLTN.domain.verification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerifyRepository extends JpaRepository<VerifyEntity, Long> {
    Optional<VerifyEntity> findByUserId(Long userId);
}
