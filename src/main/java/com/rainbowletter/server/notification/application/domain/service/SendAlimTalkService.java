package com.rainbowletter.server.notification.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.notification.application.domain.model.Notification;
import com.rainbowletter.server.notification.application.domain.model.Notification.NotificationType;
import com.rainbowletter.server.notification.application.port.in.SendAlimTalkCommand;
import com.rainbowletter.server.notification.application.port.in.SendAlimTalkUseCase;
import com.rainbowletter.server.notification.application.port.out.SaveNotificationPort;
import com.rainbowletter.server.notification.application.port.out.SendAlimTalkPort;
import com.rainbowletter.server.notification.application.port.out.dto.SendAlimTalkRequest;
import com.rainbowletter.server.notification.application.port.out.dto.SendAlimTalkRequest.SendAlimTalkButtonRequest;
import com.rainbowletter.server.notification.application.port.out.dto.SendAlimTalkResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class SendAlimTalkService implements SendAlimTalkUseCase {

    private final SendAlimTalkPort sendAlimTalkPort;
    private final SaveNotificationPort saveNotificationPort;

    @Override
    public void sendAlimTalk(final SendAlimTalkCommand command) {
        final SendAlimTalkRequest alimTalkRequest = new SendAlimTalkRequest(
            command.getReceiver(),
            command.getTemplateCode().getCode(),
            command.isUseEmTitle(),
            command.getTitle(),
            command.getFailTitle(),
            command.getContent(),
            command.getFailContent(),
            new SendAlimTalkButtonRequest(command.getButtons())
        );
        final SendAlimTalkResponse response = sendAlimTalkPort.sendAlimTalk(alimTalkRequest);

        final Notification notification = Notification.withoutId(
            command.getTitle(),
            command.getContent(),
            command.getReceiver(),
            command.getSender(),
            NotificationType.ALIM_TALK,
            response.result_code(),
            response.message()
        );
        saveNotificationPort.saveNotification(notification);
    }

}
