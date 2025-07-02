package com.rainbowletter.server.notification.application.port.in;

import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkButton;
import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkTemplateCode;
import java.util.List;

public interface GetAlimTalkTemplateUseCase {

    String getSubject(GetAlimTalkTemplateQuery query);

    String failSubject(GetAlimTalkTemplateQuery query);

    String getContent(GetAlimTalkTemplateQuery query);

    String failContent(GetAlimTalkTemplateQuery query);

    List<AlimTalkButton> getButtons(GetAlimTalkTemplateQuery query);

    record GetAlimTalkTemplateQuery(AlimTalkTemplateCode templateCode, List<Object> args) {

    }

}
