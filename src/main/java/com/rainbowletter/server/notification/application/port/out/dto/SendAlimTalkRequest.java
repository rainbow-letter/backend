package com.rainbowletter.server.notification.application.port.out.dto;

import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkButton;
import java.util.List;

public record SendAlimTalkRequest(
    String receiver,
    String templateCode,
    boolean useEmTitle,
    String title,
    String failTitle,
    String content,
    String failContent,
    SendAlimTalkButtonRequest buttons
) {

    public record SendAlimTalkButtonRequest(List<AlimTalkButton> button) {

    }

}
