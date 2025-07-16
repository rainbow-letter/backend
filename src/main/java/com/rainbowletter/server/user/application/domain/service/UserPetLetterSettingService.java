package com.rainbowletter.server.user.application.domain.service;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.UserPetInitiatedLetterJpaRepository;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.UserPetInitiatedLetter;
import com.rainbowletter.server.user.adapter.in.web.dto.PetSelectionRequest;
import com.rainbowletter.server.user.adapter.in.web.dto.UserPetLetterSettingRequest;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import com.rainbowletter.server.user.application.port.out.UpdateUserStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserPetLetterSettingService {

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;
    private final UpdateUserStatePort updateUserStatePort;
    private final UserPetInitiatedLetterJpaRepository userPetInitiatedLetterJpaRepository;

    @Transactional
    public void updatePetLetterEnabled(String email, UserPetLetterSettingRequest request) {
        if (isBlockedTime(LocalDateTime.now())) {
            throw new RainbowLetterException("현재 시간에는 선편지 설정 변경이 불가능합니다. (월/수/금 19:30~20:00 제한)", email);
        }

        final User user = loadUserPort.loadUserByEmail(email);
        user.updatePetInitiatedLetterEnabled(request.enabled());
        updateUserStatePort.updateUser(user);
    }

    private boolean isBlockedTime(LocalDateTime now) {
        DayOfWeek day = now.getDayOfWeek();
        int hour = now.getHour();
        int minute = now.getMinute();

        boolean isBlockedDay = day == DayOfWeek.MONDAY || day == DayOfWeek.WEDNESDAY || day == DayOfWeek.FRIDAY;
        boolean isBlockedTime = (hour == 19 && minute >= 30) || (hour == 20 && minute == 0);

        return isBlockedDay && isBlockedTime;
    }

    public void registerPetForInitiatedLetter(String email, PetSelectionRequest request) {
        if (isBlockedTime(LocalDateTime.now())) {
            throw new RainbowLetterException("현재 시간에는 선편지 설정 변경이 불가능합니다. (월/수/금 19:30~20:00 제한)", email);
        }

        final User user = loadUserPort.loadUserByEmail(email);
        final Pet pet = loadPetPort.loadPetByIdAndUserId(new Pet.PetId(request.petId()), user.getId());

        Long userId = user.getId().value();
        Long petId = pet.getId().value();

        boolean alreadyRegistered = userPetInitiatedLetterJpaRepository.existsByUserIdAndPetId(userId, petId);
        if (alreadyRegistered) {
            throw new RainbowLetterException("이미 등록된 아이입니다.", "Email : " + email + ", PetId : " + petId);
        }

        UserPetInitiatedLetter userPetInitiatedLetter = UserPetInitiatedLetter.builder()
            .userId(userId)
            .petId(petId)
            .build();
        userPetInitiatedLetterJpaRepository.save(userPetInitiatedLetter);
    }
}
