package com.rainbowletter.server.reply.application.domain.service;

import com.rainbowletter.server.common.application.port.out.PublishDomainEventPort;
import com.rainbowletter.server.common.util.TimeHolder;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.port.out.LoadReplyPort;
import com.rainbowletter.server.reply.application.port.out.SaveReplyPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReplySubmitScheduler {

    private final TimeHolder timeHolder;
    private final LoadReplyPort loadReplyPort;
    private final SaveReplyPort saveReplyPort;
    private final PublishDomainEventPort publishDomainEventPort;

    @Async
    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void reservationSubmit() {
        final List<Reply> replies = loadReplyPort.loadRepliesByReservation();
        for (final Reply reply : replies) {
            reply.submit(timeHolder.currentTime());
            publishDomainEventPort.publish(reply);
        }
        saveReplyPort.saveAll(replies);
    }

}
