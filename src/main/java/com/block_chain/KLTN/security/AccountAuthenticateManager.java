package com.block_chain.KLTN.security;

import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Configuration
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AccountAuthenticateManager implements AuthenticationProvider {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CustomUserDetailService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        UserEntity account = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found with email : " + username));

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new BadCredentialsException("Password is wrong.");
        }
        return new UsernamePasswordAuthenticationToken(userDetailService.loadUserByUsername(username), null);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(CustomAuthentication.class);
    }

}
