package com.rainbowletter.server.user.application.domain.model;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import java.util.Arrays;

public enum OAuthProvider {
    NONE,
    GOOGLE,
    NAVER,
    KAKAO,
    ;

    public static OAuthProvider get(final String registrationId) {
        return Arrays.stream(OAuthProvider.values())
            .filter(provider -> provider != NONE)
            .filter(provider -> provider.name().toLowerCase().equals(registrationId))
            .findAny()
            .orElseThrow(() -> new RainbowLetterException("로그인 타입 %s를 찾을 수 없습니다.", registrationId));
    }

}
