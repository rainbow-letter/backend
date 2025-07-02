package com.rainbowletter.server.common.config.security;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.common.util.JwtTokenProvider;
import com.rainbowletter.server.common.util.JwtTokenProvider.TokenInformation;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_KEY;
import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_TYPE;

@Component
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends GenericFilter {

    private final transient JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Expose-Headers", "*");

        final String token = parseTokenFromRequest(servletRequest);
        saveAuthentication(token);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String parseTokenFromRequest(final ServletRequest servletRequest) {
        final var request = (HttpServletRequest) servletRequest;
        final var bearerToken = request.getHeader(AUTHORIZATION_HEADER_KEY);
        if (StringUtils.hasText(bearerToken) &&
                bearerToken.startsWith(AUTHORIZATION_HEADER_TYPE + " ")
        ) {
            return bearerToken.substring(7);
        }
        return "INVALID_TOKEN";
    }

    private void saveAuthentication(final String token) {
        try {
            final TokenInformation decodedToken = jwtTokenProvider.parse(token);
            final Authentication authentication = buildAuthenticationFromTokenInfo(decodedToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (final RainbowLetterException exception) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }

    private Authentication buildAuthenticationFromTokenInfo(
            final TokenInformation tokenInformation) {
        final var authorities = List.of(new SimpleGrantedAuthority(tokenInformation.claimValue()));
        final var principal = new User(tokenInformation.subject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

}
