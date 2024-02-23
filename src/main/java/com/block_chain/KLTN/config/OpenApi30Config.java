package com.block_chain.KLTN.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "My API", version = "v1"),
    servers = {
        @Server(url = "http://localhost:8080", description = "Local Server")},
    security = {@SecurityRequirement(name = "Authorization")})
@SecurityScheme(
    name = "Authorization",
    type = SecuritySchemeType.APIKEY,
    in = SecuritySchemeIn.HEADER
)
public class OpenApi30Config {

}
