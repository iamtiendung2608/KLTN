package com.block_chain.KLTN.domain.organization;

public interface OrganizationService {
    void createOrganization(CreateOrganizationRequest request);

    void editOrganization(Long id, UpdateOrganizationRequest request);
}
