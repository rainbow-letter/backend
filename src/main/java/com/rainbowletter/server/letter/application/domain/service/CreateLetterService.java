package com.rainbowletter.server.letter.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.application.port.out.PublishDomainEventPort;
import com.rainbowletter.server.common.util.UuidHolder;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterStatus;
import com.rainbowletter.server.letter.application.port.in.CreateLetterCommand;
import com.rainbowletter.server.letter.application.port.in.CreateLetterUseCase;
import com.rainbowletter.server.letter.application.port.out.CountLetterPort;
import com.rainbowletter.server.letter.application.port.out.SaveLetterPort;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class CreateLetterService implements CreateLetterUseCase {

    private final UuidHolder uuidHolder;
    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;
    private final CountLetterPort countLetterPort;
    private final SaveLetterPort saveLetterPort;
    private final PublishDomainEventPort publishDomainEventPort;

    @Override
    public Long createLetter(final CreateLetterCommand command) {
        final User user = loadUserPort.loadUserByEmail(command.getEmail());
        final Pet pet = loadPetPort.loadPetByIdAndUserId(command.getPetId(), user.getId());
        final Integer lastNumber = countLetterPort.getLastNumber(user.getEmail(), pet.getId());

        final Letter letter = Letter.withoutId(
            user.getId(),
            pet.getId(),
            command.getSummary(),
            command.getContent(),
            uuidHolder.generate(),
            command.getImage(),
            lastNumber + 1,
            LetterStatus.REQUEST,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        final Letter savedLetter = saveLetterPort.save(letter);
        savedLetter.createEvent();
        publishDomainEventPort.publish(savedLetter);
        return savedLetter
            .getId()
            .value();
    }

}
