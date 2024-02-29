package com.block_chain.KLTN.config;

import com.block_chain.KLTN.common.ApiConstant;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.security.*;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;

@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/swagger-ui.html**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/webjars/**"),
            new AntPathRequestMatcher("/v3/api-docs/**"),
            new AntPathRequestMatcher("/swagger-resources/**"),
            new AntPathRequestMatcher("/favicon.ico"),
            new AntPathRequestMatcher("/actuator/**"),
            new AntPathRequestMatcher(ApiConstant.AUTH + ApiConstant.LOGIN),
            new AntPathRequestMatcher(ApiConstant.AUTH + ApiConstant.CONFIRM_SIGNUP),
            new AntPathRequestMatcher(ApiConstant.AUTH + ApiConstant.VERIFY_USER),
            new AntPathRequestMatcher(ApiConstant.AUTH + ApiConstant.SIGNUP),
            new AntPathRequestMatcher(ApiConstant.AUTH + ApiConstant.FORGOT_PASSWORD),
            new AntPathRequestMatcher(ApiConstant.AUTH + ApiConstant.RESEND_VERIFICATION),
            new AntPathRequestMatcher(ApiConstant.AUTH + ApiConstant.RESET_PASSWORD));

    private TokenAuthenticationFilter tokenAuthenticationFilter;
    private CustomUserDetailService customUserDetailService;
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(tokenAuthenticationFilter, SessionManagementFilter.class)
//            .addFilterAfter(systemAccessFilter, JwtTokenFilter.class)
                .csrf().disable()
                .cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint()).and()
                .authorizeRequests()
                .requestMatchers(PUBLIC_URLS).permitAll()
                .anyRequest().authenticated().and()
                .formLogin().disable()
                .httpBasic().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailService);
    }

    @Override
    protected AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(new AccountAuthenticateManager(userRepository, passwordEncoder(), customUserDetailService)));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

