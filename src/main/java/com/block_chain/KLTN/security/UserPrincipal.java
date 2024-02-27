package com.block_chain.KLTN.security;

import com.block_chain.KLTN.common.AppConstant;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class UserPrincipal implements UserDetails {
    private Long id;
    private String email;
    private String password;
    private UserStatus status;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String email, String password, UserStatus status, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.status = status;
        this.authorities = authorities;
    }

    public static UserPrincipal create(UserEntity user) {
        List<GrantedAuthority> authorities = user.getUserRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleCode())).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus(),
                authorities
        );
    }

    public static UserPrincipal create(UserEntity user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public boolean isSupperAdmin() {
        if (CollectionUtils.isEmpty(this.authorities)) {
            return false;
        }
        return this.authorities.stream()
                .anyMatch(i -> AppConstant.Roles.SUPER_ADMIN.getRoleCode().equals(i.getAuthority()));
    }

    public UserPrincipal setAuthorities(Collection<String> roleCodes) {
        this.authorities = roleCodes.stream()
                .map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
        return this;
    }

    public UserPrincipal setStatus(String sStatus) {
        this.status = UserStatus.valueOf(sStatus);
        return this;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.ACTIVE.equals(status);
    }
}
