package com.rainbowletter.server.notification.application.domain.model.alimtalk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlimTalkButtonType {
    WL("웹링크"),
    BK("봇키워드"),
    DS("배송조회"),
    MD("메시지전달");

    private final String buttonName;

}
