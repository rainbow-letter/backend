package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.common.config.ClientConfig;
import com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkButton;
import com.rainbowletter.server.notification.application.domain.model.mail.MailTemplateCode;
import com.rainbowletter.server.notification.application.port.in.*;
import com.rainbowletter.server.notification.application.port.in.GetAlimTalkTemplateUseCase.GetAlimTalkTemplateQuery;
import com.rainbowletter.server.notification.application.port.in.GetMailTemplateUseCase.GetMailTemplateQuery;
import com.rainbowletter.server.pet.application.port.in.dto.PetSummary;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetter;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.SubmitPetInitiatedLetterEvent;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

import static com.rainbowletter.server.notification.application.domain.model.alimtalk.AlimTalkTemplateCode.PET_INITIATED_LETTER;

@Service
@RequiredArgsConstructor
public class SendNotificationToPetInitiatedLetterEventHandler {
    private static final Pattern EMOJI_PATTERN = Pattern.compile("[\\p{So}\\p{Cn}]");

    private final ClientConfig clientConfig;

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;

    private final SendMailUseCase sendMailUseCase;
    private final GetMailTemplateUseCase getMailTemplateUseCase;

    private final SendAlimTalkUseCase sendAlimTalkUseCase;
    private final GetAlimTalkTemplateUseCase getAlimTalkTemplateUseCase;

    @Async
    @TransactionalEventListener
    public void handleSubmitPetInitiatedLetter(SubmitPetInitiatedLetterEvent event) {
        PetInitiatedLetter letter = event.petInitiatedLetter();
        User user = loadUserPort.loadUserById(new User.UserId(letter.getUserId()));
        PetSummary pet = loadPetPort.findPetSummaryById(letter.getPetId(), letter.getUserId());

        sendAlimTalk(user, pet, letter);
        sendMail(user, pet, letter);
    }

    private void sendMail(User user, PetSummary pet, PetInitiatedLetter letter) {
        GetMailTemplateQuery titleQuery = GetMailTemplateQuery.titleQuery(MailTemplateCode.PET_INITIATED_LETTER, pet.name());
        String title = getMailTemplateUseCase.getTitle(titleQuery);

        GetMailTemplateQuery contentQuery = GetMailTemplateQuery.contentQuery(
            MailTemplateCode.PET_INITIATED_LETTER,
            user.getEmail(),
            List.of(
                "/pre-share/" + letter.getShareLink() + "?utm_source=petinitiatedlettercheck",
                pet.name()
            )
        );
        String content = getMailTemplateUseCase.getContent(contentQuery);

        SendMailCommand command = new SendMailCommand(user.getEmail(), "SERVER", title, content);
        sendMailUseCase.sendMail(command);
    }

    private void sendAlimTalk(User user, PetSummary pet, PetInitiatedLetter letter) {
        if (!StringUtils.hasText(user.getPhoneNumber())) {
            return;
        }

        GetAlimTalkTemplateQuery titleQuery = new GetAlimTalkTemplateQuery(PET_INITIATED_LETTER, List.of());
        String title = getAlimTalkTemplateUseCase.getSubject(titleQuery);

        String petName = EMOJI_PATTERN.matcher(pet.name()).replaceAll("");

        GetAlimTalkTemplateQuery contentQuery = new GetAlimTalkTemplateQuery(
            PET_INITIATED_LETTER,
            List.of(
                petName,
                pet.owner(),
                petName
            )
        );
        String content = getAlimTalkTemplateUseCase.getContent(contentQuery);

        GetAlimTalkTemplateQuery failTitleQuery = new GetAlimTalkTemplateQuery(PET_INITIATED_LETTER, List.of());
        String failTitle = getAlimTalkTemplateUseCase.failSubject(failTitleQuery);

        GetAlimTalkTemplateQuery failContentQuery = new GetAlimTalkTemplateQuery(
            PET_INITIATED_LETTER,
            List.of(letter.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), petName)
        );
        String failContent = getAlimTalkTemplateUseCase.failContent(failContentQuery);

        GetAlimTalkTemplateQuery buttonQuery = new GetAlimTalkTemplateQuery(
            PET_INITIATED_LETTER,
            List.of(clientConfig.getBaseUrl() + "/share/" + letter.getShareLink() + "?utm_source=petinitiatedlettercheck")
        );
        List<AlimTalkButton> buttons = getAlimTalkTemplateUseCase.getButtons(buttonQuery);

        SendAlimTalkCommand command = new SendAlimTalkCommand(
            user.getPhoneNumber(),
            "SERVER",
            PET_INITIATED_LETTER,
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
