package com.github.classpick.global.security.oauth;

import com.github.classpick.user.repository.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class OAuth2GoogleUser implements OAuth2User, UserDetails {

    private final UserEntity user;
    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;

    public OAuth2GoogleUser(OAuth2User user) {

        this.user = null;
        this.attributes = user.getAttributes();
        this.authorities = user.getAuthorities();
    }

    public OAuth2GoogleUser(UserEntity user) {

        this.user = user;
        this.attributes = Collections.emptyMap();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_%s".formatted(user.getRole())));
    }

    @Override
    public String getPassword() {

        return "(EMPTY)";
    }

    @Override
    public String getUsername() {

        return (String) attributes.get("sub");
    }

    @Override
    public boolean isAccountNonExpired() {

        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {

        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {

        return UserDetails.super.isEnabled();
    }

    @Override
    public String getName() {

        return (String) attributes.get("name");
    }

    public String getEmail() {

        return (String) attributes.get("email");
    }
}
