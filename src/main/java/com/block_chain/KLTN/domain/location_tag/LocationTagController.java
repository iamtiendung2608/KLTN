package com.block_chain.KLTN.domain.location_tag;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationTagController {

    private final LocationTagQueryService locationTagQueryService;

    @GetMapping("")
    List<LocationTagResponse> getLocationTag(LocationTagSearchRequest request) {
        return locationTagQueryService.getLocationTag(request);
    }

    @GetMapping("/{id}")
    LocationTagResponse getLocationTagById(@PathVariable("id") Long id) {
        return locationTagQueryService.getLocationTagById(id);
    }
}
