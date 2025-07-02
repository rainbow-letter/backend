package com.rainbowletter.server.reply.application.port.out;

import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;

public interface DeleteReplyPort {

    void deleteByLetterId(LetterId letterId);

}
