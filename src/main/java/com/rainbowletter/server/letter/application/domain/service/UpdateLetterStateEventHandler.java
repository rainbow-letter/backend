package com.rainbowletter.server.letter.application.domain.service;

import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import com.rainbowletter.server.letter.application.port.out.SaveLetterPort;
import com.rainbowletter.server.reply.application.domain.model.SubmitReplyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
class UpdateLetterStateEventHandler {

    private final SaveLetterPort saveLetterPort;
    private final LoadLetterPort loadLetterPort;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void fromSubmitReply(final SubmitReplyEvent event) {
        final Letter letter = loadLetterPort.loadLetterById(event.reply().getLetterId());
        letter.receiveReply();
        saveLetterPort.save(letter);
    }

}
