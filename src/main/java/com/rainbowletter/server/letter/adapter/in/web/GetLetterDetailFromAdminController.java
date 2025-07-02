package com.rainbowletter.server.letter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.letter.adapter.in.web.dto.LetterAdminDetailResponse;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.letter.application.port.in.GetLetterByIdUseCase;
import com.rainbowletter.server.letter.application.port.in.GetLetterByIdUseCase.GetLetterByIdQuery;
import com.rainbowletter.server.letter.application.port.in.GetLetterCountUseCase;
import com.rainbowletter.server.letter.application.port.in.GetLetterCountUseCase.GetLetterCountQuery;
import com.rainbowletter.server.letter.application.port.in.GetRecentLettersFromAdminUseCase;
import com.rainbowletter.server.letter.application.port.in.GetRecentLettersFromAdminUseCase.GetRecentLettersQuery;
import com.rainbowletter.server.letter.application.port.in.dto.LetterResponse;
import com.rainbowletter.server.pet.application.port.in.GetPetByLetterUseCase;
import com.rainbowletter.server.pet.application.port.in.GetPetByLetterUseCase.GetPetByLetterIdQuery;
import com.rainbowletter.server.reply.application.port.in.GetReplyFromAdminUseCase;
import com.rainbowletter.server.reply.application.port.in.GetReplyFromAdminUseCase.GetReplyFromAdminQuery;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import com.rainbowletter.server.user.application.port.in.GetUserInformationUseCase;
import com.rainbowletter.server.user.application.port.in.GetUserInformationUseCase.GetUserInformationByIdQuery;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/letters")
@Tag(name = "letter")
class GetLetterDetailFromAdminController {

    private final GetUserInformationUseCase getUserInformationUseCase;
    private final GetLetterCountUseCase getLetterCountUseCase;
    private final GetPetByLetterUseCase getPetByLetterUseCase;
    private final GetLetterByIdUseCase getLetterByIdUseCase;
    private final GetRecentLettersFromAdminUseCase getRecentLettersFromAdminUseCase;
    private final GetReplyFromAdminUseCase getReplyFromAdminUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<LetterAdminDetailResponse> getLetterDetail(
        @PathVariable("id") final Long id,
        @RequestParam("user") final Long userId
    ) {
        final UserId domainUserId = new UserId(userId);
        final var userQuery = new GetUserInformationByIdQuery(domainUserId);
        final var userResponse = getUserInformationUseCase.getUserInformation(userQuery);

        final var letterCountQuery = new GetLetterCountQuery(domainUserId);
        final Long letterCount = getLetterCountUseCase.getLetterCount(letterCountQuery);

        final LetterId domainLetterId = new LetterId(id);
        final var petQuery = new GetPetByLetterIdQuery(domainLetterId);
        final var petResponse = getPetByLetterUseCase.getPetByLetterId(petQuery);

        final var letterQuery = new GetLetterByIdQuery(domainLetterId);
        final LetterResponse letterResponse = getLetterByIdUseCase.getLetter(letterQuery);

        final var recentLettersQuery = new GetRecentLettersQuery(domainUserId);
        final var recentLettersResponse = getRecentLettersFromAdminUseCase
            .getRecentLetters(recentLettersQuery);

        final var replyQuery = new GetReplyFromAdminQuery(domainLetterId);
        final var replyResponse = getReplyFromAdminUseCase.getReplyFromAdmin(replyQuery);

        final LetterAdminDetailResponse response = LetterAdminDetailResponse.of(
            userResponse,
            letterCount,
            petResponse,
            letterResponse,
            replyResponse,
            recentLettersResponse
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
