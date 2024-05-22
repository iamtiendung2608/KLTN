package com.block_chain.KLTN.domain.organization;

import java.util.Optional;

public interface OrganizationQueryService {
    Optional<OrganizationResponse> getOrganization(Long id);
}
