package com.block_chain.KLTN.domain.organization;

public record UpdateOrganizationRequest(
        String name,
        String description,
        OrganizationScale scale,
        OrganizationCategory category,
        OrganizationScope scope
) {
}
