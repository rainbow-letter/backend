package com.rainbowletter.server.letter.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.letter.application.port.in.GetLetterByEmailAndIdUseCase;
import com.rainbowletter.server.letter.application.port.in.dto.LetterResponse;
import com.rainbowletter.server.letter.application.port.out.LoadLetterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetLetterByEmailAndIdService implements GetLetterByEmailAndIdUseCase {

    private final LoadLetterPort loadLetterPort;

    @Override
    public LetterResponse getLetter(final GetLetterByEmailQuery query) {
        return LetterResponse.from(
            loadLetterPort.loadLetterByEmailAndId(
                query.email(),
                query.id()
            )
        );
    }

}
