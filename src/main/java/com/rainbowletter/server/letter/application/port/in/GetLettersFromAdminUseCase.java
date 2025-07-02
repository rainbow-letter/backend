package com.rainbowletter.server.letter.application.port.in;

import com.rainbowletter.server.letter.application.port.in.dto.LetterAdminPageResponse;
import org.springframework.data.domain.Page;

public interface GetLettersFromAdminUseCase {

    Page<LetterAdminPageResponse> getLetters(GetLettersFromAdminQuery query);

}
