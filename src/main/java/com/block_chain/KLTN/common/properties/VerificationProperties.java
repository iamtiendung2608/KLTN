package com.block_chain.KLTN.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties("verification")
@Getter
@Setter
public class VerificationProperties {
    @NotNull
    private long waiting;
}
