package com.rainbowletter.server.letter.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.letter.application.port.in.GetLetterBoxQuery;
import com.rainbowletter.server.letter.application.port.in.GetLetterBoxUseCase;
import com.rainbowletter.server.letter.application.port.in.dto.LetterBoxResponses;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetLetterBoxService implements GetLetterBoxUseCase {

    private final LoadLetterPort loadLetterPort;

    @Override
    public LetterBoxResponses getLetterBox(final GetLetterBoxQuery query) {
        return LetterBoxResponses.from(
            loadLetterPort.loadLetterBox(
                query.getEmail(),
                query.getPetId(),
                query.getStartDateTime(),
                query.getEndDateTime()
            )
        );
    }

}
