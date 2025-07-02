package com.rainbowletter.server.notification.application.port.in;

import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkButton;
import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkTemplateCode;
import java.util.List;
import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class SendAlimTalkCommand {

    String receiver;
    String sender;
    AlimTalkTemplateCode templateCode;
    boolean useEmTitle;
    String title;
    String content;
    String failTitle;
    String failContent;
    List<AlimTalkButton> buttons;

}
