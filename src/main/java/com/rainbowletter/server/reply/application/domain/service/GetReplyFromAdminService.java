package com.rainbowletter.server.reply.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.port.in.GetReplyFromAdminUseCase;
import com.rainbowletter.server.reply.application.port.in.dto.ReplyAdminResponse;
import com.rainbowletter.server.reply.application.port.out.LoadReplyPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetReplyFromAdminService implements GetReplyFromAdminUseCase {

    private final LoadReplyPort loadReplyPort;

    @Override
    public ReplyAdminResponse getReplyFromAdmin(final GetReplyFromAdminQuery query) {
        final Optional<Reply> reply = loadReplyPort
            .loadReplyByLetterIdAndStatus(query.letterId(), null);
        return reply.map(ReplyAdminResponse::from).orElse(null);
    }

}
