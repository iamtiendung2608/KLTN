package com.block_chain.KLTN.domain.post_offices;

import org.springframework.stereotype.Service;

@Service
public class DefaultPostOfficesQueryService implements PostOfficesQueryService {
    private final PostOfficesRepository postOfficesRepository;

    public DefaultPostOfficesQueryService(PostOfficesRepository postOfficesRepository) {
        this.postOfficesRepository = postOfficesRepository;
    }

    @Override
    public PostOfficesResponse getPostOffices(Long id) {
        return null;
    }
}
