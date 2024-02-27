package com.block_chain.KLTN.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.auth")
public class AuthProperties {
    @NotNull
    private String tokenSecret;

    @NotNull
    private String tokenExpirationMsec;
}
