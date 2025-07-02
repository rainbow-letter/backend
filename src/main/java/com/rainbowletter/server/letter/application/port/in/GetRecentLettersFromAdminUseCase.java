package com.rainbowletter.server.letter.application.port.in;

import com.rainbowletter.server.letter.application.port.in.dto.LetterAdminRecentResponse;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import java.util.List;

public interface GetRecentLettersFromAdminUseCase {

    List<LetterAdminRecentResponse> getRecentLetters(GetRecentLettersQuery query);

    record GetRecentLettersQuery(UserId userId) {

    }

}
