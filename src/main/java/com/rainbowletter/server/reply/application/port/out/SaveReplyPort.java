package com.rainbowletter.server.reply.application.port.out;

import com.rainbowletter.server.reply.application.domain.model.Reply;
import java.util.List;

public interface SaveReplyPort {

    Reply save(Reply reply);

    void saveAll(List<Reply> replies);

}
