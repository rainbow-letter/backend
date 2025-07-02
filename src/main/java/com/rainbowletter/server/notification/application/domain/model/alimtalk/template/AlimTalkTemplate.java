package com.rainbowletter.server.notification.application.domain.model.alimtalk.template;

import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkButton;
import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkTemplateCode;
import java.util.List;

public interface AlimTalkTemplate {

    boolean support(AlimTalkTemplateCode templateCode);

    String subject(Object... args);

    String failSubject(Object... args);

    String content(Object... args);

    String failContent(Object... args);

    List<AlimTalkButton> buttons(Object... args);

}
