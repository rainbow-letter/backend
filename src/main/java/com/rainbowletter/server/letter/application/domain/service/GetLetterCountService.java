package com.rainbowletter.server.letter.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.letter.application.port.in.GetLetterCountUseCase;
import com.rainbowletter.server.letter.application.port.out.CountLetterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetLetterCountService implements GetLetterCountUseCase {

    private final CountLetterPort countLetterPort;

    @Override
    public Long getLetterCount(final GetLetterCountQuery query) {
        return countLetterPort.countByUserId(query.userId());
    }

}
