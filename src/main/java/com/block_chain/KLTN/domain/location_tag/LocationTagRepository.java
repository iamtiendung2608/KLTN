package com.block_chain.KLTN.domain.location_tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationTagRepository extends JpaRepository<LocationTagEntity, Long> {
}
