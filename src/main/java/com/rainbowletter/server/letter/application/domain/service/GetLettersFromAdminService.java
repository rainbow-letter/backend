package com.rainbowletter.server.letter.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.letter.application.port.in.GetLettersFromAdminQuery;
import com.rainbowletter.server.letter.application.port.in.GetLettersFromAdminUseCase;
import com.rainbowletter.server.letter.application.port.in.dto.LetterAdminPageResponse;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetLettersFromAdminService implements GetLettersFromAdminUseCase {

    private final LoadLetterPort loadLetterPort;

    @Override
    public Page<LetterAdminPageResponse> getLetters(final GetLettersFromAdminQuery query) {
        return loadLetterPort.loadLettersFromAdmin(
            query.getStartDateTime(),
            query.getEndDateTime(),
            query.getStatus(),
            query.getEmail(),
            query.getInspect(),
            query.getPageable()
        );
    }

}
