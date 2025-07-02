package com.rainbowletter.server.user.adapter.out.infrastructure;

import static com.rainbowletter.server.user.application.domain.model.User.UserStatus;

import com.rainbowletter.server.user.application.domain.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class NativeUserDetails implements UserDetails {

    private final transient User user;
    private final List<GrantedAuthority> roles = new ArrayList<>();

    public NativeUserDetails(final User user) {
        this.user = user;
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return user.hasDifferentStatus(UserStatus.LEAVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.hasDifferentStatus(UserStatus.INACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.hasDifferentStatus(UserStatus.LOCK);
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.hasDifferentStatus(UserStatus.SLEEP);
    }

}
