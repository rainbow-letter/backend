package com.rainbowletter.server.common.config.security;

import com.rainbowletter.server.common.config.ClientConfig;
import com.rainbowletter.server.common.util.JwtTokenProvider;
import com.rainbowletter.server.common.util.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ClientConfig clientConfig;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Authentication authentication
    ) throws IOException {
        final String username = SecurityUtils.parseEmail(authentication);
        final String role = SecurityUtils.parseAuthority(authentication);
        final String token = jwtTokenProvider.create(username, role);
        final String redirectUrl = clientConfig.getBaseUrl() + "/oauth/success?token=" + token;
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

}
