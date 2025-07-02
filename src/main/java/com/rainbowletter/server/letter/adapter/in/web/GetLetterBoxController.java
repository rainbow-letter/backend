package com.rainbowletter.server.letter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.letter.application.port.in.GetLetterBoxQuery;
import com.rainbowletter.server.letter.application.port.in.GetLetterBoxUseCase;
import com.rainbowletter.server.letter.application.port.in.dto.LetterBoxResponses;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/letters")
@Tag(name = "letter")
class GetLetterBoxController {

    private final GetLetterBoxUseCase getLetterBoxUseCase;

    @GetMapping("/box")
    ResponseEntity<LetterBoxResponses> getLetterBox(
        @RequestParam("pet") final Long petId,
        @RequestParam(value = "start", required = false) final LocalDate start,
        @RequestParam(value = "end", required = false) final LocalDate end
    ) {
        final var query = new GetLetterBoxQuery(
            SecurityUtils.getEmail(),
            new PetId(petId),
            start,
            end
        );
        final LetterBoxResponses response = getLetterBoxUseCase.getLetterBox(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
