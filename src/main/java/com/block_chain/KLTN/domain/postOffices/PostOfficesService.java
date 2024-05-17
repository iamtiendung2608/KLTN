package com.block_chain.KLTN.domain.postOffices;

public interface PostOfficesService {
    boolean checkWallet();

    CreatePostOfficesResponse create(PostOfficesRequest request);
    PostOfficesResponse update(Long id, UpdatePostOfficesRequest request);

    void delete(Long id);
}
