package com.rainbowletter.server.letter.application.port.in;

import com.rainbowletter.server.user.application.domain.model.User.UserId;

public interface GetLetterCountUseCase {

    Long getLetterCount(GetLetterCountQuery query);

    record GetLetterCountQuery(UserId userId) {

    }

}
