package com.rainbowletter.server.pet.application.domain.service;

import com.rainbowletter.server.pet.application.port.out.ResetFavoriteStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
class ResetFavoriteScheduler {

    private final ResetFavoriteStatePort resetFavoriteStatePort;

    @Async
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void resetFavorite() {
        resetFavoriteStatePort.resetFavorite();
    }

}
