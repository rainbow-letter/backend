package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.ai.application.domain.model.AiSetting;
import com.rainbowletter.server.ai.application.port.out.LoadSettingPort;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterJpaRepository;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.UserPetInitiatedLetterPersistenceAdapter;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.GeneratePetInitiatedLetterEvent;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetter;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetterStatus;
import com.rainbowletter.server.petinitiatedletter.application.port.in.dto.UserPetPairDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PetInitiatedLetterRecordCreationScheduler {

    private final UserPetInitiatedLetterPersistenceAdapter userPetInitiatedLetterPersistenceAdapter;
    private final PetInitiatedLetterJpaRepository petInitiatedLetterJpaRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final LoadSettingPort loadSettingPort;

    @Async
    @Scheduled(cron = "0 30 19 * * *")
    @Transactional
    public void createPetInitiatedLetterRecords() {
        List<UserPetPairDto> userPetPairs = userPetInitiatedLetterPersistenceAdapter.findAllUserPetPairs();

        AiSetting aiSetting = loadSettingPort.loadPetInitiatedLetterSetting();

        List<PetInitiatedLetter> letters = userPetPairs.stream()
            .map(dto -> PetInitiatedLetter.builder()
                .userId(dto.userId())
                .petId(dto.petId())
                .shareLink(UUID.randomUUID())
                .promptType(aiSetting.getSelectPrompt())
                .status(PetInitiatedLetterStatus.SCHEDULED)
                .readStatus(false)
                .build()
            )
            .toList();

        List<PetInitiatedLetter> letterList = petInitiatedLetterJpaRepository.saveAll(letters);

        eventPublisher.publishEvent(
            new GeneratePetInitiatedLetterEvent(
                letterList.stream().map(PetInitiatedLetter::getId).toList()
            )
        );
    }

}
