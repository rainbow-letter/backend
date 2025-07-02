package com.rainbowletter.server.notification.application.port.out;

import com.rainbowletter.server.notification.application.port.out.dto.SendAlimTalkRequest;
import com.rainbowletter.server.notification.application.port.out.dto.SendAlimTalkResponse;

public interface SendAlimTalkPort {

    SendAlimTalkResponse sendAlimTalk(SendAlimTalkRequest request);

}
