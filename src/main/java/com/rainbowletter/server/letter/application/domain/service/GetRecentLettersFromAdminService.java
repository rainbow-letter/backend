package com.rainbowletter.server.letter.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.letter.application.port.in.GetRecentLettersFromAdminUseCase;
import com.rainbowletter.server.letter.application.port.in.dto.LetterAdminRecentResponse;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetRecentLettersFromAdminService implements GetRecentLettersFromAdminUseCase {

    private final LoadLetterPort loadLetterPort;

    @Override
    public List<LetterAdminRecentResponse> getRecentLetters(final GetRecentLettersQuery query) {
        return loadLetterPort.loadRecentLettersByUserId(query.userId());
    }

}
