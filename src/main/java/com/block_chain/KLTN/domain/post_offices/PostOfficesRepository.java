package com.block_chain.KLTN.domain.post_offices;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostOfficesRepository extends JpaRepository<PostOfficesEntity, Long> {
}
