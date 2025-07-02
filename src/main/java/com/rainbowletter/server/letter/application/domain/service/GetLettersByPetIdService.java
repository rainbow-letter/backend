package com.rainbowletter.server.letter.application.domain.service;

import com.rainbowletter.server.letter.adapter.in.web.dto.LetterCollectResponse;
import com.rainbowletter.server.letter.adapter.in.web.dto.RetrieveLetterRequest;
import com.rainbowletter.server.letter.adapter.out.dto.PaginationInfo;
import com.rainbowletter.server.letter.application.port.in.dto.LetterSimpleResponse;
import com.rainbowletter.server.letter.application.port.in.dto.LetterSimpleWithSequenceResponse;
import com.rainbowletter.server.letter.application.port.out.CountLetterPort;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import com.rainbowletter.server.pet.application.port.in.dto.PetSummary;
import com.rainbowletter.server.pet.application.port.out.LoadPetPort;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetLettersByPetIdService {

    private final CountLetterPort countLetterPort;
    private final LoadLetterPort loadLetterPort;
    private final LoadPetPort loadPetPort;
    private final LoadUserPort loadUserPort;

    public LetterCollectResponse findByPetId(Long petId, RetrieveLetterRequest query, String email) {
        User user = loadUserPort.loadUserByEmail(email);
        PetSummary petSummary = loadPetPort.findPetSummaryById(petId, user.getId().value());

        List<LetterSimpleResponse> letters = loadLetterPort.findByPetId(petId, query);

        boolean hasNext = letters.size() > query.limit();

        long total = countLetterPort.countByPetIdAndEndDate(petId, query.endDate());

        List<LetterSimpleWithSequenceResponse> result = new ArrayList<>();
        long seq = total - (query.after() != null ? getOffsetFromAfter(letters, query.after()) : 0);
        for (LetterSimpleResponse letter : hasNext ? letters.subList(0, query.limit()) : letters) {
            result.add(LetterSimpleWithSequenceResponse.from(letter, seq--));
        }

        String next = letters.isEmpty()
            ? null
            : buildNextUrl(letters.get(Math.min(query.limit(), letters.size()) - 1).id(), query.limit());

        return new LetterCollectResponse(petSummary, result, new PaginationInfo(next));
    }

    private long getOffsetFromAfter(List<LetterSimpleResponse> letters, Long after) {
        for (int i = 0; i < letters.size(); i++) {
            if (letters.get(i).id().equals(after)) {
                return i + 1;
            }
        }
        return 0;
    }

    private String buildNextUrl(Long lastId, int limit) {
        return "https://api.rainbowletter.co.kr?after=" + lastId + "&limit=" + limit;
    }
}
