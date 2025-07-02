package com.rainbowletter.server.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AnonymousAllowUri implements AllowUri {
    USER_CREATE("/api/users/create"),
    USER_LOGIN("/api/users/login"),
    FIND_USER_PASSWORD("/api/users/find-password"),
    ;

    private final String uri;

}
