package com.block_chain.KLTN.security;

import com.block_chain.KLTN.domain.user.UserEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class UserAuthenticateManager implements AuthenticationProvider {
    private UserEntity user;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new UsernamePasswordAuthenticationToken(UserPrincipal.create(user), null);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(CustomAuthentication.class);
    }
}
