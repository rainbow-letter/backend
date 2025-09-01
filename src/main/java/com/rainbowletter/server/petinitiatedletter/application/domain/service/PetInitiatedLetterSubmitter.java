package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.common.util.TimeHolder;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterJpaRepository;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetter;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.SubmitPetInitiatedLetterEvent;
import com.rainbowletter.server.slack.application.domain.service.SlackErrorReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PetInitiatedLetterSubmitter {

    private final PetInitiatedLetterJpaRepository petInitiatedLetterJpaRepository;
    private final TimeHolder timeHolder;
    private final ApplicationEventPublisher eventPublisher;
    private final SlackErrorReportService slackErrorReportService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void submit(PetInitiatedLetter letter) {
        try {
            letter.submit(timeHolder.currentTime());
            petInitiatedLetterJpaRepository.save(letter);
            eventPublisher.publishEvent(new SubmitPetInitiatedLetterEvent(letter));
        } catch (Exception e) {
            log.error("선편지 발송 실패, letterId={}", letter.getId(), e);
            slackErrorReportService.sendSubmitPetLetterErrorReportToSlack(letter.getId(), e);
            throw e; // 현재 편지만 롤백
        }
    }

}
