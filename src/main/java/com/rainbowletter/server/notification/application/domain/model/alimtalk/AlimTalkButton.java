package com.rainbowletter.server.notification.application.domain.model.alimtalk;

import static com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkButtonType.BK;
import static com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkButtonType.WL;

public record AlimTalkButton(
    String name,
    String linkType,
    String linkTypeName,
    String linkMo,
    String linkPc,
    String schemeIos,
    String schemeAndroid
) {

    public static AlimTalkButton createWebLink(final String name, final String link) {
        return new AlimTalkButton(name, WL.name(), WL.getButtonName(), link, link, null, null);
    }

    public static AlimTalkButton createBotKeyword(final String name) {
        return new AlimTalkButton(name, BK.name(), BK.getButtonName(), null, null, null, null);
    }

}
