package com.rainbowletter.server.reply.application.port.in;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;

public interface RegenerateReplyUseCase {

    void regenerateReply(RegenerateReplyCommand command);

    record RegenerateReplyCommand(LetterId letterId) {

    }

}
