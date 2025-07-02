package com.rainbowletter.server.user.adapter.out.infrastructure;

import com.rainbowletter.server.common.annotation.InfraAdapter;
import com.rainbowletter.server.common.util.JwtTokenProvider;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.user.application.port.out.NativeUserLoginPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@InfraAdapter
@RequiredArgsConstructor
class NativeUserLoginHandler implements NativeUserLoginPort {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationBuilder;

    @Override
    public String login(final String email, final String password) {
        final var token = new UsernamePasswordAuthenticationToken(email, password);
        final var authentication = authenticationBuilder.getObject().authenticate(token);
        final String username = SecurityUtils.parseEmail(authentication);
        final String role = SecurityUtils.parseAuthority(authentication);
        return jwtTokenProvider.create(username, role);
    }

}
