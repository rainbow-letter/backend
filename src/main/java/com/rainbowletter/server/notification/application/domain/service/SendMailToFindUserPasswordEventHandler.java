package com.rainbowletter.server.notification.application.domain.service;

import static com.rainbowletter.server.notification.application.port.in.GetMailTemplateUseCase.GetMailTemplateQuery;

import com.rainbowletter.server.notification.application.domain.model.mail.MailTemplateCode;
import com.rainbowletter.server.notification.application.port.in.GetMailTemplateUseCase;
import com.rainbowletter.server.notification.application.port.in.SendMailCommand;
import com.rainbowletter.server.notification.application.port.in.SendMailUseCase;
import com.rainbowletter.server.user.application.domain.model.FindUserPasswordMailEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Profile("!test")
@Service
@RequiredArgsConstructor
class SendMailToFindUserPasswordEventHandler {

    private final SendMailUseCase sendMailUseCase;
    private final GetMailTemplateUseCase getMailTemplateUseCase;

    @Async
    @EventListener
    @Transactional
    public void fromFindUserPasswordEvent(final FindUserPasswordMailEvent event) {
        final String receiver = event.user().getEmail();
        final GetMailTemplateQuery titleQuery = GetMailTemplateQuery.titleQuery(
            MailTemplateCode.FIND_PASSWORD,
            "비밀번호를 다시 설정해주세요!"
        );
        final GetMailTemplateQuery contentQuery = GetMailTemplateQuery.contentQuery(
            MailTemplateCode.FIND_PASSWORD,
            receiver,
            List.of()
        );

        final String title = getMailTemplateUseCase.getTitle(titleQuery);
        final String content = getMailTemplateUseCase.getContent(contentQuery);
        final SendMailCommand command = new SendMailCommand(receiver, "SERVER", title, content);

        sendMailUseCase.sendMail(command);
    }

}
