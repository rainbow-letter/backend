package com.rainbowletter.server.letter.application.domain.service;

import com.rainbowletter.server.common.application.port.out.PublishDomainEventPort;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.port.out.DeleteLetterPort;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import com.rainbowletter.server.log.application.port.in.LogEventCommand;
import com.rainbowletter.server.log.application.port.in.LogEventUseCase;
import com.rainbowletter.server.pet.application.domain.model.DeletePetEvent;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
class DeleteLetterEventHandler {

    private final LoadLetterPort loadLetterPort;
    private final DeleteLetterPort deleteLetterPort;

    private final LogEventUseCase logEventUseCase;
    private final PublishDomainEventPort publishDomainEventPort;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void fromPetDelete(final DeletePetEvent event) {
        final List<Letter> letters = loadLetterPort.loadLettersByPetId(event.pet().getId());
        final List<LogEventCommand> logEventCommands = new ArrayList<>();

        for (final Letter letter : letters) {
            letter.delete();
            publishDomainEventPort.publish(letter);
            final LogEventCommand logEventCommand = new LogEventCommand(
                letter.getId().value(),
                letter.getUserId().value(),
                "LETTER",
                "DELETE",
                "반려동물 삭제로 인한 편지 삭제"
            );
            logEventCommands.add(logEventCommand);
        }

        deleteLetterPort.deleteAll(letters);
        logEventUseCase.successLog(logEventCommands);
    }

}
