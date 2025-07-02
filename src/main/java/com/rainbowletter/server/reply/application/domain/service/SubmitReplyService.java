package com.rainbowletter.server.reply.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.common.application.port.out.PublishDomainEventPort;
import com.rainbowletter.server.common.util.TimeHolder;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;
import com.rainbowletter.server.reply.application.port.in.SubmitReplyUseCase;
import com.rainbowletter.server.reply.application.port.out.LoadReplyPort;
import com.rainbowletter.server.reply.application.port.out.SaveReplyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class SubmitReplyService implements SubmitReplyUseCase {

    private final TimeHolder timeHolder;
    private final LoadReplyPort loadReplyPort;
    private final SaveReplyPort saveReplyPort;
    private final PublishDomainEventPort publishDomainEventPort;

    @Override
    public void submitReply(final ReplyId replyId) {
        final Reply reply = loadReplyPort.loadReplyById(replyId);
        reply.submit(timeHolder.currentTime());
        saveReplyPort.save(reply);
        publishDomainEventPort.publish(reply);
    }

}
