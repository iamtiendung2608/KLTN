package com.block_chain.KLTN.domain.organization;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrganizationCategory {
    FASHION("FASHION"),
    SPORT("SPORT"),
    FRAGILE("FRAGILE");

    private final String value;
}
