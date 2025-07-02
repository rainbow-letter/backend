package com.rainbowletter.server.reply.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.port.in.UpdateReplyContentCommand;
import com.rainbowletter.server.reply.application.port.in.UpdateReplyContentUseCase;
import com.rainbowletter.server.reply.application.port.out.LoadReplyPort;
import com.rainbowletter.server.reply.application.port.out.SaveReplyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class UpdateReplyContentService implements UpdateReplyContentUseCase {

    private final LoadReplyPort loadReplyPort;
    private final SaveReplyPort saveReplyPort;

    @Override
    public void updateReplyContent(final UpdateReplyContentCommand command) {
        final Reply reply = loadReplyPort.loadReplyById(command.getReplyId());
        reply.update(
            command.getPromptType(),
            command.getSummary(),
            command.getContent()
        );
        saveReplyPort.save(reply);
    }

}
