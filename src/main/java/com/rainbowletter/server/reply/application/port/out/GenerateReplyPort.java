package com.rainbowletter.server.reply.application.port.out;

import com.rainbowletter.server.reply.application.domain.model.Reply;

public interface GenerateReplyPort {

    Reply generate(GenerateReplyCommand command);

}
