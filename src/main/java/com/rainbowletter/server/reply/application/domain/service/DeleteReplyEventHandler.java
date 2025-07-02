package com.rainbowletter.server.reply.application.domain.service;

import com.rainbowletter.server.letter.application.domain.model.DeleteLetterEvent;
import com.rainbowletter.server.log.application.port.in.LogEventCommand;
import com.rainbowletter.server.log.application.port.in.LogEventUseCase;
import com.rainbowletter.server.reply.application.port.out.DeleteReplyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
class DeleteReplyEventHandler {

    private final DeleteReplyPort deleteReplyPort;
    private final LogEventUseCase logEventUseCase;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void fromLetterDelete(final DeleteLetterEvent event) {
        deleteReplyPort.deleteByLetterId(event.letter().getId());
        logEventUseCase.successLog(
            new LogEventCommand(
                event.letter().getId().value(),
                event.letter().getUserId().value(),
                "REPLY",
                "DELETE",
                "편지 삭제로 인해 답장 삭제"
            )
        );
    }

}
