package com.rainbowletter.server.common.util;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

    public static String getEmail() {
        final Authentication authentication = getAuthentication();
        return parseEmail(authentication);
    }

    public static String getRole() {
        final Authentication authentication = getAuthentication();
        return parseAuthority(authentication);
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String parseEmail(final Authentication authentication) {
        if (Objects.isNull(authentication) || authentication.getPrincipal() instanceof String) {
            throw new RainbowLetterException("incorrect.user.credentials");
        }
        return authentication.getName();
    }

    public static String parseAuthority(final Authentication authentication) {
        return authentication.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .findAny()
            .orElseThrow(() -> new RainbowLetterException("user.credential.information.not.found"));
    }

}
