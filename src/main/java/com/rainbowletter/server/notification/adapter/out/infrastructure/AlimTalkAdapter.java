package com.rainbowletter.server.notification.adapter.out.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbowletter.server.common.annotation.InfraAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.common.config.NotificationConfig;
import com.rainbowletter.server.common.util.SystemEnvironment;
import com.rainbowletter.server.notification.application.port.out.SendAlimTalkPort;
import com.rainbowletter.server.notification.application.port.out.dto.SendAlimTalkRequest;
import com.rainbowletter.server.notification.application.port.out.dto.SendAlimTalkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@InfraAdapter
@RequiredArgsConstructor
class AlimTalkAdapter implements SendAlimTalkPort {

    private final ObjectMapper objectMapper;
    private final SystemEnvironment systemEnvironment;
    private final NotificationConfig notificationConfig;
    private final AligoAlimTalkClient aligoAlimTalkClient;

    @Override
    public SendAlimTalkResponse sendAlimTalk(final SendAlimTalkRequest request) {
        try {
            final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            if (systemEnvironment.isActiveTest()) {
                body.add("testMode", "Y");
            }
            if (request.useEmTitle()) {
                body.add("emtitle_1", request.title());
            }
            body.add("apikey", notificationConfig.getAccessKey());
            body.add("userid", "rainbowletter");
            body.add("senderkey", notificationConfig.getSenderKey());
            body.add("tpl_code", request.templateCode());
            body.add("sender", notificationConfig.getSender());
            body.add("receiver_1", request.receiver());
            body.add("subject_1", request.title());
            body.add("message_1", request.content());
            body.add("button_1", objectMapper.writeValueAsString(request.buttons()));
            body.add("failover", "Y");
            body.add("fsubject_1", request.failTitle());
            body.add("fmessage_1", request.failContent());
            return aligoAlimTalkClient.sendAlimTalk(body);
        } catch (final JsonProcessingException exception) {
            throw new RainbowLetterException("fail.alimtalk.template.convert.json", exception);
        }
    }

}
