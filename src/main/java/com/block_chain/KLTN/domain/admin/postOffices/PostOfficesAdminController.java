package com.block_chain.KLTN.domain.admin.postOffices;

import com.block_chain.KLTN.domain.postOffices.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/admin/post-offices")
@RequiredArgsConstructor
public class PostOfficesAdminController {
    private final PostOfficesQueryService postOfficesQueryService;
    private final PostOfficesService postOfficesService;

    @PostMapping("")
    public CreatePostOfficesResponse createPostOffices(@RequestBody PostOfficesRequest request) {
        return postOfficesService.create(request);
    }
    
    @GetMapping("/{id}")
    public PostOfficesResponse getPostOffices(@PathVariable("id") Long id) {
        return postOfficesQueryService.getPostOffices(id);
    }

    @GetMapping("")
    public Page<PostOfficesResponse> searchPostOffices(PostOfficesSearchRequest request, Pageable pageable) {
        return postOfficesQueryService.searchPostOffices(request, pageable);
    }

    @PutMapping("/{id}")
    public PostOfficesResponse updatePostOffices(@PathVariable("id") Long id, @RequestBody @Valid UpdatePostOfficesRequest request) {
        return postOfficesService.update(id, request);
    }

    @PatchMapping("/{id}")
    public void deletePostOffices(@PathVariable("id") Long id) {
        postOfficesService.delete(id);
    }

    @GetMapping("/check")
    public boolean checkWallet() {
        return postOfficesService.checkWallet();
    }
}
