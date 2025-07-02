package com.rainbowletter.server.letter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.letter.adapter.in.web.dto.LetterDetailResponse;
import com.rainbowletter.server.letter.application.port.in.GetLetterByShareLinkUseCase;
import com.rainbowletter.server.letter.application.port.in.GetLetterByShareLinkUseCase.GetLetterByShareLinkQuery;
import com.rainbowletter.server.pet.application.port.in.GetPetByLetterUseCase;
import com.rainbowletter.server.pet.application.port.in.GetPetByLetterUseCase.GetPetByShareLinkQuery;
import com.rainbowletter.server.reply.application.port.in.GetReplyByLetterUseCase;
import com.rainbowletter.server.reply.application.port.in.GetReplyByLetterUseCase.GetReplyByShareLinkQuery;
import java.util.UUID;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/letters")
@Tag(name = "letter")
class GetLetterByShareLinkController {

    private final GetPetByLetterUseCase getPetByLetterUseCase;
    private final GetLetterByShareLinkUseCase getLetterByShareLinkUseCase;
    private final GetReplyByLetterUseCase getReplyByLetterUseCase;

    @GetMapping("/share/{shareLink}")
    ResponseEntity<LetterDetailResponse> getLetterByShareLink(
        @PathVariable("shareLink") final String shareLink
    ) {
        final UUID shareUUID = UUID.fromString(shareLink);

        final var petQuery = new GetPetByShareLinkQuery(shareUUID);
        final var petResponse = getPetByLetterUseCase.getPetByShareLink(petQuery);

        final var letterQuery = new GetLetterByShareLinkQuery(shareUUID);
        final var letterResponse = getLetterByShareLinkUseCase.getLetter(letterQuery);

        final var replyQuery = new GetReplyByShareLinkQuery(shareUUID);
        final var replyResponse = getReplyByLetterUseCase.getReplyByShareLink(replyQuery);

        final LetterDetailResponse response = LetterDetailResponse.of(
            petResponse,
            letterResponse,
            replyResponse
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
