package com.rainbowletter.server.reply.application.domain.service;

import com.rainbowletter.server.ai.application.domain.model.Parameter.FirstLetter;
import com.rainbowletter.server.ai.application.domain.model.Parameter.LetterCount;
import com.rainbowletter.server.letter.application.domain.model.CreateLetterEvent;
import com.rainbowletter.server.letter.application.port.out.CountLetterPort;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.port.out.GenerateReplyCommand;
import com.rainbowletter.server.reply.application.port.out.GenerateReplyPort;
import com.rainbowletter.server.reply.application.port.out.SaveReplyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
class CreateReplyEventHandler {

    private final LoadPetPort loadPetPort;
    private final CountLetterPort countLetterPort;
    private final GenerateReplyPort generateReplyPort;
    private final SaveReplyPort saveReplyPort;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void fromLetterCreate(final CreateLetterEvent event) {
        final Pet pet = loadPetPort.loadPetByIdAndUserId(
            event.letter().getPetId(),
            event.letter().getUserId()
        );
        final Long letterCount = countLetterPort.countByPetId(event.letter().getPetId());
        final boolean isFirstLetter = letterCount <= 1;

        final var command = new GenerateReplyCommand(
            pet,
            event.letter(),
            new LetterCount(letterCount),
            new FirstLetter(isFirstLetter)
        );
        final Reply reply = generateReplyPort.generate(command);
        saveReplyPort.save(reply);
    }

}
