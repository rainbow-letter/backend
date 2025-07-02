package com.rainbowletter.server.letter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.letter.adapter.in.web.dto.LetterDetailResponse;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.letter.application.port.in.GetLetterByEmailAndIdUseCase;
import com.rainbowletter.server.letter.application.port.in.GetLetterByEmailAndIdUseCase.GetLetterByEmailQuery;
import com.rainbowletter.server.pet.application.port.in.GetPetByLetterUseCase;
import com.rainbowletter.server.pet.application.port.in.GetPetByLetterUseCase.GetPetByLetterIdQuery;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import com.rainbowletter.server.reply.application.port.in.GetReplyByLetterUseCase;
import com.rainbowletter.server.reply.application.port.in.GetReplyByLetterUseCase.GetReplyByLetterQuery;
import com.rainbowletter.server.reply.application.port.in.dto.ReplyResponse;
import io.swagger.v3.oas.annotations.Operation;
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
class GetLetterDetailController {

    private final GetPetByLetterUseCase getPetByLetterUseCase;
    private final GetLetterByEmailAndIdUseCase getLetterByEmailAndIdUseCase;
    private final GetReplyByLetterUseCase getReplyByLetterUseCase;

    @Operation(summary = "편지 ID로 조회")
    @GetMapping("/{letterId}")
    ResponseEntity<LetterDetailResponse> getLetterDetail(@PathVariable("letterId") final Long id) {
        final String email = SecurityUtils.getEmail();
        final LetterId letterId = new LetterId(id);

        final var petQuery = new GetPetByLetterIdQuery(letterId);
        final var petResponse = getPetByLetterUseCase.getPetByLetterId(petQuery);

        final var letterQuery = new GetLetterByEmailQuery(email, letterId);
        final var letterResponse = getLetterByEmailAndIdUseCase.getLetter(letterQuery);

        final var replyQuery = new GetReplyByLetterQuery(letterId, ReplyStatus.REPLY);
        final ReplyResponse replyResponse = getReplyByLetterUseCase.getReplyByLetter(replyQuery);

        final LetterDetailResponse response = LetterDetailResponse.of(
            petResponse,
            letterResponse,
            replyResponse
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
