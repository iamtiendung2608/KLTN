package com.block_chain.KLTN.domain.organization;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultOrganizationQueryService implements OrganizationQueryService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;

    @Override
    public Optional<OrganizationResponse> getOrganization(Long id) {
        return organizationRepository.findById(id).map(organizationMapper::toResponse);
    }
}
