package com.rainbowletter.server.reply.application.port.out;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadReplyPort {

    Reply loadReplyById(ReplyId id);

    Optional<Reply> loadReplyByLetterIdAndStatus(LetterId letterId, ReplyStatus status);

    Reply loadReplyByShareLink(UUID shareLink);

    List<Reply> loadRepliesByReservation();

}
