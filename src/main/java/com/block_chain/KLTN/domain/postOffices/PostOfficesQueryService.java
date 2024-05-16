package com.block_chain.KLTN.domain.postOffices;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostOfficesQueryService {
    PostOfficesResponse getPostOffices(Long id);

    Page<PostOfficesResponse> searchPostOffices(PostOfficesSearchRequest request, Pageable pageable);
}
