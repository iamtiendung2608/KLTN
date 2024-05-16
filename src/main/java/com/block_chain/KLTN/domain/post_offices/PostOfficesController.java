package com.block_chain.KLTN.domain.post_offices;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/post-offices")
@RestController
@RequiredArgsConstructor
public class PostOfficesController {
    private final PostOfficesQueryService postOfficesQueryService;
    private final PostOfficesService postOfficesService;

    @GetMapping("/{id}")
    public PostOfficesResponse getPostOffices(@PathVariable("id") Long id) {
        return postOfficesQueryService.getPostOffices(id);
    }

    @GetMapping("")
    public Page<PostOfficesResponse> searchPostOffices(PostOfficesSearchRequest request, Pageable pageable) {
        return postOfficesQueryService.searchPostOffices(request, pageable);
    }

    @GetMapping("/check")
    public boolean checkWallet() {
        return postOfficesService.checkWallet();
    }
}
