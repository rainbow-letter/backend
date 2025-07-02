package com.rainbowletter.server.common.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

class CustomAuthenticationEntryPoint extends AbstractAuthenticationHandler
    implements AuthenticationEntryPoint {

    @Override
    public void commence(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final AuthenticationException authException
    ) throws IOException {
        handle(HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다.", response);
    }

}
