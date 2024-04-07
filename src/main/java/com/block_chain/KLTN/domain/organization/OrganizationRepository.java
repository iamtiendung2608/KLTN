package com.block_chain.KLTN.domain.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
}
