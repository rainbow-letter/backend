package com.rainbowletter.server.common.config.security.uri;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccessAllowUri implements AllowUri {
    DOCS("/docs/index.html"),
    GET_FAQS("/api/faqs"),
    GET_IMAGES("/api/images/resources/**"),
    SHARE_LETTER("/api/letters/share/**"),
    SHARE_PET_INITIATED_LETTER("/api/pet-initiated-letters/share/**"),
    LOG_USER_AGENT("/api/data/**"),
    ;

    private final String uri;

}
