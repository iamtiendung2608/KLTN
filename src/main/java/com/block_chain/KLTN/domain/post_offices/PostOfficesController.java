package com.block_chain.KLTN.domain.post_offices;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/post-offices")
@RestController
@RequiredArgsConstructor
public class PostOfficesController {
    private PostOfficesService postOfficesService;
    private PostOfficesQueryService postOfficesQueryService;

    @GetMapping("/{id}")
    public PostOfficesResponse getPostOffices(@PathVariable("id") Long id) {
        return postOfficesQueryService.getPostOffices(id);
    }

    //TODO: add paging search here
}
