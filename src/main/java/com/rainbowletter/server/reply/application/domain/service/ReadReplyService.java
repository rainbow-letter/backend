package com.rainbowletter.server.reply.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.letter.application.domain.model.Letter;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;
import com.rainbowletter.server.reply.application.port.in.ReadReplyUseCase;
import com.rainbowletter.server.reply.application.port.out.LoadReplyPort;
import com.rainbowletter.server.reply.application.port.out.SaveReplyPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class ReadReplyService implements ReadReplyUseCase {

    private final LoadReplyPort loadReplyPort;
    private final LoadLetterPort loadLetterPort;
    private final SaveReplyPort saveReplyPort;

    @Override
    public void readReply(final ReplyId replyId) {
        final Reply reply = loadReplyPort.loadReplyById(replyId);
        final Letter letter = loadLetterPort.loadLetterById(reply.getLetterId());
        reply.read();
        letter.read();
        saveReplyPort.save(reply);
    }

}
