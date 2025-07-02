package com.rainbowletter.server.user.adapter.out.infrastructure;

import com.rainbowletter.server.user.application.domain.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

class OAuthUserDetails implements OAuth2User {

    private final User user;
    private final Map<String, Object> attributes;
    private final List<GrantedAuthority> roles = new ArrayList<>();

    public OAuthUserDetails(final User user, final Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

}
