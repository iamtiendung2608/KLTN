package com.block_chain.KLTN.security;

import com.block_chain.KLTN.common.properties.AuthProperties;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class TokenProvider {
    private UserRepository userRepository;
    private AuthProperties authProperties;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Instant now = Instant.now();
        Instant expiryInstant = now.plus(Long.parseLong(authProperties.getTokenExpirationMsec()), ChronoUnit.MILLIS);
        Date expiryDate = Date.from(expiryInstant);

        List<String> roles = userPrincipal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        UserEntity user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User"));

        return Jwts.builder()
                .claim("id", userPrincipal.getId())
                .claim("role", roles)
                .claim("email", userPrincipal.getEmail())
                .claim("status", userPrincipal.getStatus().name())
                .claim("authenticationObject", "User")
                .setIssuedAt(Date.from(now))
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, authProperties.getTokenSecret())
                .compact();
    }

    public UserPrincipal getUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(authProperties.getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return new UserPrincipal().setId(claims.get("id", Long.class))
                .setEmail(claims.get("email", String.class))
                .setStatus(claims.get("status", String.class))
                .setAuthorities(claims.get("role", Collection.class));
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(authProperties.getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
