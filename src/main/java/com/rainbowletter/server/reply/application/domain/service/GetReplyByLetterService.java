package com.rainbowletter.server.reply.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.port.in.GetReplyByLetterUseCase;
import com.rainbowletter.server.reply.application.port.in.dto.ReplyResponse;
import com.rainbowletter.server.reply.application.port.out.LoadReplyPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetReplyByLetterService implements GetReplyByLetterUseCase {

    private final LoadReplyPort loadReplyPort;

    @Override
    public ReplyResponse getReplyByLetter(final GetReplyByLetterQuery query) {
        final Optional<Reply> reply = loadReplyPort
            .loadReplyByLetterIdAndStatus(query.letterId(), query.status());
        return reply.map(ReplyResponse::from).orElse(null);
    }

    @Override
    public ReplyResponse getReplyByShareLink(final GetReplyByShareLinkQuery query) {
        final Reply reply = loadReplyPort.loadReplyByShareLink(query.shareLink());
        return ReplyResponse.from(reply);
    }

}
