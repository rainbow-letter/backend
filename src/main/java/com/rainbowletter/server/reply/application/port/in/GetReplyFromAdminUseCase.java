package com.rainbowletter.server.reply.application.port.in;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.reply.application.port.in.dto.ReplyAdminResponse;

public interface GetReplyFromAdminUseCase {

    ReplyAdminResponse getReplyFromAdmin(GetReplyFromAdminQuery query);

    record GetReplyFromAdminQuery(LetterId letterId) {

    }

}
