package com.block_chain.KLTN.domain.organization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrganizationScope {
    PERSONAL("PERSONAL"),
    ORGANIZATION("ORGANIZATION");

    private final String value;
}
