package com.rainbowletter.server.notification.application.domain.service;

import static com.rainbowletter.server.notification.application.domain.model.Notification.NotificationType;
import static com.rainbowletter.server.notification.application.domain.model.Notification.withoutId;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.notification.application.domain.model.Notification;
import com.rainbowletter.server.notification.application.port.in.SendMailCommand;
import com.rainbowletter.server.notification.application.port.in.SendMailUseCase;
import com.rainbowletter.server.notification.application.port.out.SaveNotificationPort;
import com.rainbowletter.server.notification.application.port.out.SendMailPort;
import com.rainbowletter.server.notification.application.port.out.dto.SendMailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class SendMailService implements SendMailUseCase {

    private final SendMailPort sendMailPort;
    private final SaveNotificationPort saveNotificationPort;

    @Override
    public void sendMail(final SendMailCommand command) {
        final SendMailRequest mailRequest = new SendMailRequest(
            command.getReceiver(),
            command.getTitle(),
            command.getContent()
        );
        sendMailPort.sendMail(mailRequest);

        final Notification notification = withoutId(
            command.getTitle(),
            command.getContent(),
            command.getReceiver(),
            command.getSender(),
            NotificationType.MAIL,
            0,
            "SUCCESS"
        );
        saveNotificationPort.saveNotification(notification);
    }

}
