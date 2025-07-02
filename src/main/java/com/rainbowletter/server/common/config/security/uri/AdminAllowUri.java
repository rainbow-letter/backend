package com.rainbowletter.server.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminAllowUri implements AllowUri {
    ADMIN("/api/admins/**"),
    ;

    private final String uri;

}
