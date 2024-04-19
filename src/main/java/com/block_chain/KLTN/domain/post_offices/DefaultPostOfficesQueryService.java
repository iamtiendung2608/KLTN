package com.block_chain.KLTN.domain.post_offices;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DefaultPostOfficesQueryService implements PostOfficesQueryService {
    private final PostOfficesRepository postOfficesRepository;
    private final PostOfficesMapper postOfficesMapper;

    public DefaultPostOfficesQueryService(PostOfficesRepository postOfficesRepository, PostOfficesMapper postOfficesMapper) {
        this.postOfficesRepository = postOfficesRepository;
        this.postOfficesMapper = postOfficesMapper;
    }

    @Override
    public PostOfficesResponse getPostOffices(Long id) {
        return postOfficesRepository.findById(id).map(postOfficesMapper::toResponse).orElse(null);
    }

    @Override
    public Page<PostOfficesResponse> searchPostOffices(PostOfficesSearchRequest request, Pageable pageable) {
        return postOfficesRepository.findAll(request.toPredicate(), pageable).map(postOfficesMapper::toResponse);
    }
}
