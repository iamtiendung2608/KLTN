package com.block_chain.KLTN.domain.organization;

import javax.validation.constraints.NotNull;

public record CreateOrganizationRequest(
    @NotNull Long id,
    @NotNull String name,
    String description,
    OrganizationScale scale,
    OrganizationCategory category,
    OrganizationScope scope
) {
}
