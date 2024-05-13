package com.block_chain.KLTN.domain.post_offices;

public interface PostOfficesService {
    boolean checkWallet();

    CreatePostOfficesResponse create(PostOfficesRequest request);
    PostOfficesResponse update(Long id, UpdatePostOfficesRequest request);

    void delete(Long id);
}
