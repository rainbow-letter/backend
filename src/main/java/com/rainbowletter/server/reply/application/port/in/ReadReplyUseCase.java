package com.rainbowletter.server.reply.application.port.in;

import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;

public interface ReadReplyUseCase {

    void readReply(ReplyId replyId);

}
