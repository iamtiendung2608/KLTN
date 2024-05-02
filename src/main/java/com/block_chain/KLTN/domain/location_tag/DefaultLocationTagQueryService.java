package com.block_chain.KLTN.domain.location_tag;

import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultLocationTagQueryService implements LocationTagQueryService{
    private final LocationTagRepository locationTagRepository;
    private final LocationTagMapper locationTagMapper;


    @Override
    public Page<LocationTagResponse> getLocationTag(LocationTagSearchRequest request, Pageable pageable) {
        return locationTagRepository.findAll(request.toPredicate(), pageable).map(locationTagMapper::toResponse);
    }

    @Override
    public LocationTagResponse getLocationTagById(Long id) {
        return locationTagMapper.toResponse(locationTagRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "LocationTag")));
    }
}
