package com.block_chain.KLTN.domain.location_tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationTagController {

    private final LocationTagQueryService locationTagQueryService;

    @GetMapping("")
    Page<LocationTagResponse> getLocationTag(LocationTagSearchRequest request, Pageable pageable) {
        return locationTagQueryService.getLocationTag(request, pageable);
    }

    @GetMapping("/{id}")
    LocationTagResponse getLocationTagById(@PathVariable("id") Long id) {
        return locationTagQueryService.getLocationTagById(id);
    }
}
