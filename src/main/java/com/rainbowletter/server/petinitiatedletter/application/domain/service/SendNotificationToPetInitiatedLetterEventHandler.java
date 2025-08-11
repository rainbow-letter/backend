package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.notification.application.domain.model.mail.MailTemplateCode;
import com.rainbowletter.server.notification.application.port.in.GetMailTemplateUseCase;
import com.rainbowletter.server.notification.application.port.in.GetMailTemplateUseCase.GetMailTemplateQuery;
import com.rainbowletter.server.notification.application.port.in.SendMailCommand;
import com.rainbowletter.server.notification.application.port.in.SendMailUseCase;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetter;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.SubmitPetInitiatedLetterEvent;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SendNotificationToPetInitiatedLetterEventHandler {

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;

    private final SendMailUseCase sendMailUseCase;
    private final GetMailTemplateUseCase getMailTemplateUseCase;

    @Async
    @TransactionalEventListener
    public void handleSubmitPetInitiatedLetter(SubmitPetInitiatedLetterEvent event) {
        PetInitiatedLetter letter = event.petInitiatedLetter();
        User user = loadUserPort.loadUserById(new User.UserId(letter.getUserId()));
        Pet pet = loadPetPort.loadPetByIdAndUserId(new Pet.PetId(letter.getPetId()), user.getId());

//        sendAlimTalk(user, pet, letter);
        sendMail(user, pet, letter);
    }

    private void sendMail(User user, Pet pet, PetInitiatedLetter letter) {
        GetMailTemplateQuery titleQuery = GetMailTemplateQuery.titleQuery(MailTemplateCode.PET_INITIATED_LETTER, pet.getName());
        String title = getMailTemplateUseCase.getTitle(titleQuery);

        final var contentQuery = GetMailTemplateQuery.contentQuery(
            MailTemplateCode.PET_INITIATED_LETTER,
            user.getEmail(),
            List.of(
                "/share/" + letter.getShareLink(),
                pet.getName()
            )
        );
        final String content = getMailTemplateUseCase.getContent(contentQuery);

        final SendMailCommand command = new SendMailCommand(user.getEmail(), "SERVER", title, content);
        sendMailUseCase.sendMail(command);
    }

}
