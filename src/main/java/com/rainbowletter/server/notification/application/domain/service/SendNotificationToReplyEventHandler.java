package com.rainbowletter.server.notification.application.domain.service;

import com.rainbowletter.server.common.config.ClientConfig;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkButton;
import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkTemplateCode;
import com.rainbowletter.server.notification.application.domain.model.mail.MailTemplateCode;
import com.rainbowletter.server.notification.application.port.in.*;
import com.rainbowletter.server.notification.application.port.in.GetAlimTalkTemplateUseCase.GetAlimTalkTemplateQuery;
import com.rainbowletter.server.notification.application.port.in.GetMailTemplateUseCase.GetMailTemplateQuery;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.reply.application.domain.model.SubmitReplyEvent;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

@Profile("!test")
@Service
@RequiredArgsConstructor
class SendNotificationToReplyEventHandler {
    private static final Pattern EMOJI_PATTERN = Pattern.compile("[\\p{So}\\p{Cn}]");

    private final ClientConfig clientConfig;

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;
    private final LoadLetterPort loadLetterPort;

    private final SendMailUseCase sendMailUseCase;
    private final GetMailTemplateUseCase getMailTemplateUseCase;

    private final SendAlimTalkUseCase sendAlimTalkUseCase;
    private final GetAlimTalkTemplateUseCase getAlimTalkTemplateUseCase;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void fromSubmitReply(final SubmitReplyEvent event) {
        final Letter letter = loadLetterPort.loadLetterById(event.reply().getLetterId());
        final Pet pet = loadPetPort.loadPetByLetterId(letter.getId());
        final User user = loadUserPort.loadUserById(pet.getUserId());

        sendAlimTalk(user, pet, letter);
        sendMail(user, pet, letter);
    }

    private void sendMail(
            final User user,
            final Pet pet,
            final Letter letter
    ) {
        final var titleQuery = GetMailTemplateQuery.titleQuery(MailTemplateCode.REPLY, pet.getName());
        final String title = getMailTemplateUseCase.getTitle(titleQuery);

        final var contentQuery = GetMailTemplateQuery.contentQuery(
                MailTemplateCode.REPLY,
                user.getEmail(),
                List.of(
                        "/share/" + letter.getShareLink() + "?utm_source=replycheck",
                        pet.getName()
                )
        );
        final String content = getMailTemplateUseCase.getContent(contentQuery);

        final SendMailCommand command = new SendMailCommand(user.getEmail(), "SERVER", title, content);
        sendMailUseCase.sendMail(command);
    }

    private void sendAlimTalk(
            final User user,
            final Pet pet,
            final Letter letter
    ) {
        if (!StringUtils.hasText(user.getPhoneNumber())) {
            return;
        }

        final var titleQuery = new GetAlimTalkTemplateQuery(AlimTalkTemplateCode.REPLY, List.of());
        final String title = getAlimTalkTemplateUseCase.getSubject(titleQuery);

        var petName = EMOJI_PATTERN.matcher(pet.getName()).replaceAll("");

        final var contentQuery = new GetAlimTalkTemplateQuery(
                AlimTalkTemplateCode.REPLY,
                List.of(
                        petName,
                        pet.getOwner(),
                        letter.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        petName
                )
        );
        final String content = getAlimTalkTemplateUseCase.getContent(contentQuery);

        final var failTitleQuery = new GetAlimTalkTemplateQuery(AlimTalkTemplateCode.REPLY, List.of());
        final String failTitle = getAlimTalkTemplateUseCase.failSubject(failTitleQuery);

        final var failContentQuery = new GetAlimTalkTemplateQuery(
                AlimTalkTemplateCode.REPLY,
                List.of(letter.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), petName)
        );
        final String failContent = getAlimTalkTemplateUseCase.failContent(failContentQuery);

        final var buttonQuery = new GetAlimTalkTemplateQuery(
                AlimTalkTemplateCode.REPLY,
                List.of(clientConfig.getBaseUrl() + "/share/" + letter.getShareLink() + "?utm_source=replycheck")
        );
        final List<AlimTalkButton> buttons = getAlimTalkTemplateUseCase.getButtons(buttonQuery);

        final SendAlimTalkCommand command = new SendAlimTalkCommand(
                user.getPhoneNumber(),
                "SERVER",
                AlimTalkTemplateCode.REPLY,
                false,
                title,
                content,
                failTitle,
                failContent,
                buttons
        );
        sendAlimTalkUseCase.sendAlimTalk(command);
    }

}
