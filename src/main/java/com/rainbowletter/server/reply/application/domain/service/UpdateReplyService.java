package com.rainbowletter.server.reply.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.port.in.UpdateReplyCommand;
import com.rainbowletter.server.reply.application.port.in.UpdateReplyUseCase;
import com.rainbowletter.server.reply.application.port.out.LoadReplyPort;
import com.rainbowletter.server.reply.application.port.out.SaveReplyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class UpdateReplyService implements UpdateReplyUseCase {

    private final LoadReplyPort loadReplyPort;
    private final SaveReplyPort saveReplyPort;

    @Override
    public void updateReply(final UpdateReplyCommand command) {
        final Reply reply = loadReplyPort.loadReplyById(command.getReplyId());
        reply.update(
            command.getPromptType(),
            command.getSummary(),
            command.getContent()
        );
        saveReplyPort.save(reply);
    }

}
