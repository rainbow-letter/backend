package com.rainbowletter.server.letter.application.domain.service;

import com.rainbowletter.server.letter.adapter.in.web.dto.LetterCollectResponse;
import com.rainbowletter.server.letter.adapter.in.web.dto.RetrieveLetterRequest;
import com.rainbowletter.server.letter.adapter.out.dto.PaginationInfo;
import com.rainbowletter.server.letter.application.port.in.dto.LetterSimpleResponse;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import com.rainbowletter.server.pet.application.port.in.dto.PetSummary;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterSimpleResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.out.persistence.PetInitiatedLetterPersistenceAdapter;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetLettersByPetIdService {

    private final LoadLetterPort loadLetterPort;
    private final LoadPetPort loadPetPort;
    private final LoadUserPort loadUserPort;
    private final PetInitiatedLetterPersistenceAdapter petInitiatedLetterPersistenceAdapter;

    public LetterCollectResponse findByPetId(Long petId, RetrieveLetterRequest query, String email) {
        User user = loadUserPort.loadUserByEmail(email);
        PetSummary petSummary = loadPetPort.findPetSummaryById(petId, user.getId().value());

        List<PetInitiatedLetterSimpleResponse> petInitiatedLetters =
            petInitiatedLetterPersistenceAdapter.findByPetId(petId, query);

        List<LetterSimpleResponse> letters = loadLetterPort.findByPetId(petId, query);

        boolean hasNext = letters.size() > query.limit();
        List<LetterSimpleResponse> result = hasNext
            ? letters.subList(0, query.limit())
            : letters;

        String next = letters.isEmpty()
            ? null
            : buildNextUrl(letters.get(Math.min(query.limit(), letters.size()) - 1).id(), query.limit());

        return new LetterCollectResponse(petSummary, result, petInitiatedLetters, new PaginationInfo(next));
    }

    private String buildNextUrl(Long lastId, int limit) {
        return "?after=" + lastId + "&limit=" + limit;
    }
}
