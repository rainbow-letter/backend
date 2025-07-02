package com.rainbowletter.server.reply.application.port.in;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import com.rainbowletter.server.reply.application.port.in.dto.ReplyResponse;
import java.util.UUID;

public interface GetReplyByLetterUseCase {

    ReplyResponse getReplyByLetter(GetReplyByLetterQuery query);

    ReplyResponse getReplyByShareLink(GetReplyByShareLinkQuery query);

    record GetReplyByLetterQuery(LetterId letterId, ReplyStatus status) {

    }

    record GetReplyByShareLinkQuery(UUID shareLink) {

    }

}
