package com.rainbowletter.server.user.application.domain.service;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.pet.application.domain.model.Pet;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.UserPetInitiatedLetterJpaRepository;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.UserPetInitiatedLetterPersistenceAdapter;
import com.rainbowletter.server.petinitiatedletter.application.domain.model.UserPetInitiatedLetter;
import com.rainbowletter.server.user.adapter.in.web.dto.PetSelectionRequest;
import com.rainbowletter.server.user.adapter.in.web.dto.PetSelectionResponse;
import com.rainbowletter.server.user.adapter.in.web.dto.UserPetLetterSettingRequest;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import com.rainbowletter.server.user.application.port.out.UpdateUserStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPetLetterSettingService {

    private final LoadUserPort loadUserPort;
    private final LoadPetPort loadPetPort;
    private final UpdateUserStatePort updateUserStatePort;
    private final UserPetInitiatedLetterJpaRepository userPetInitiatedLetterJpaRepository;
    private final UserPetInitiatedLetterPersistenceAdapter userPetInitiatedLetterPersistenceAdapter;

    @Transactional
    public void updatePetLetterEnabled(String email, UserPetLetterSettingRequest request) {
        validateNotInRestrictedTime(email, LocalDateTime.now());
        final User user = loadUserPort.loadUserByEmail(email);
        user.updatePetInitiatedLetterEnabled(request.enabled());
        if (!request.enabled()) {
            userPetInitiatedLetterJpaRepository.deleteByUserId(user.getId().value());
        }
        updateUserStatePort.updateUser(user);
    }

    @Transactional
    public List<PetSelectionResponse> registerInitiatedLetterPet(String email, PetSelectionRequest request) {
        final LocalDateTime now = LocalDateTime.now();
        validateNotInRestrictedTime(email, now);

        User user = loadUserPort.loadUserByEmail(email);
        validateUserPetInitiatedLetterEnabled(user);

        UserPetIds ids = getUserPetIds(user, request.petId());

        if (userPetInitiatedLetterJpaRepository.existsByUserIdAndPetId(ids.userId(), ids.petId())) {
            throw new RainbowLetterException("이미 등록된 아이입니다.", formatDetail(email, ids.petId()));
        }

        UserPetInitiatedLetter entity = UserPetInitiatedLetter.builder()
            .userId(ids.userId())
            .petId(ids.petId())
            .createdAt(LocalDateTime.now())
            .build();
        userPetInitiatedLetterJpaRepository.save(entity);

        return userPetInitiatedLetterPersistenceAdapter.findByUserId(ids.userId());
    }

    @Transactional
    public List<PetSelectionResponse> removeInitiatedLetterPet(String email, PetSelectionRequest request) {
        validateNotInRestrictedTime(email, LocalDateTime.now());

        User user = loadUserPort.loadUserByEmail(email);
        validateUserPetInitiatedLetterEnabled(user);

        UserPetIds ids = getUserPetIds(user, request.petId());

        if (!userPetInitiatedLetterJpaRepository.existsByUserIdAndPetId(ids.userId(), ids.petId())) {
            throw new RainbowLetterException("등록되지 않은 펫입니다.", formatDetail(email, ids.petId()));
        }

        userPetInitiatedLetterJpaRepository.deleteByUserIdAndPetId(ids.userId(), ids.petId());

        return userPetInitiatedLetterPersistenceAdapter.findByUserId(ids.userId());
    }

    @Transactional(readOnly = true)
    public List<PetSelectionResponse> getInitiatedLetterPets(String email) {
        User user = loadUserPort.loadUserByEmail(email);
        validateUserPetInitiatedLetterEnabled(user);

        return userPetInitiatedLetterPersistenceAdapter.findByUserId(user.getId().value());
    }

    private UserPetIds getUserPetIds(User user, Long petId) {
        Pet pet = loadPetPort.loadPetByIdAndUserId(new Pet.PetId(petId), user.getId());
        return new UserPetIds(user.getId().value(), pet.getId().value());
    }

    private void validateUserPetInitiatedLetterEnabled(User user) {
        if (!user.isPetInitiatedLetterEnabled()) {
            throw new RainbowLetterException("아이에게 먼저 편지 받기 기능이 켜져 있지 않습니다.", user.getEmail());
        }
    }

    private void validateNotInRestrictedTime(String email, LocalDateTime now) {
        if (isBlockedTime(now)) {
            throw new RainbowLetterException("현재 시간에는 선편지 설정 변경이 불가능합니다. (월/수/금 19:00~21:00 제한)", email);
        }
    }

    private boolean isBlockedTime(LocalDateTime now) {
        DayOfWeek day = now.getDayOfWeek();
        int hour = now.getHour();
        int minute = now.getMinute();

        boolean isBlockedDay = day == DayOfWeek.FRIDAY;
        boolean isBlockedTime = (hour == 19) || (hour == 21 && minute == 0);

        return isBlockedDay && isBlockedTime;
    }

    private String formatDetail(String email, Long petId) {
        return String.format("Email: %s, PetId: %d", email, petId);
    }

    private record UserPetIds(Long userId, Long petId) {
    }
}
