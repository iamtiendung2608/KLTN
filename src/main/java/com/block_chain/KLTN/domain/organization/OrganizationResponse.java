package com.block_chain.KLTN.domain.organization;

public record OrganizationResponse(
    Long id,
    String name,
    String description,
    OrganizationScale scale,
    OrganizationCategory category,
    OrganizationScope scope
) {
}
