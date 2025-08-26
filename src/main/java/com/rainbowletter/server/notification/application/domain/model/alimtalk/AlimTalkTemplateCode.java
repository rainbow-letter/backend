package com.rainbowletter.server.notification.application.domain.model.alimtalk;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlimTalkTemplateCode {
    REPLY("TX_0503"),
    PET_INITIATED_LETTER("UB_8222"),
    ;

    private final String code;

}
