package com.rainbowletter.server.petinitiatedletter.application.domain.service;

import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterPersistenceAdapter;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetInitiatedLetterService {

    private final LoadUserPort loadUserPort;
    private final PetInitiatedLetterPersistenceAdapter petInitiatedLetterPersistenceAdapter;

    public List<PetInitiatedLetterResponse> getPetInitiatedLetters(String email) {
        final User user = loadUserPort.loadUserByEmail(email);
        petInitiatedLetterPersistenceAdapter.getPetInitiatedLetters(user.getId().value());

        return null;
    }
}
