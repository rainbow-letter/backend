package com.rainbowletter.server.reply.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.util.TimeHolder;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;
import com.rainbowletter.server.reply.application.port.in.InspectReplyUseCase;
import com.rainbowletter.server.reply.application.port.out.LoadReplyPort;
import com.rainbowletter.server.reply.application.port.out.SaveReplyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class InspectReplyService implements InspectReplyUseCase {

    private final TimeHolder timeHolder;
    private final LoadReplyPort loadReplyPort;
    private final SaveReplyPort saveReplyPort;

    @Override
    public void inspectReply(final ReplyId replyId) {
        final Reply reply = loadReplyPort.loadReplyById(replyId);
        reply.inspect(timeHolder.currentTime());
        saveReplyPort.save(reply);
    }

}
