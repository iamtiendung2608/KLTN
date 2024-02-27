package com.block_chain.KLTN.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomAuthentication extends UsernamePasswordAuthenticationToken {
    public CustomAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
