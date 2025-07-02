package com.rainbowletter.server.letter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.letter.adapter.in.web.dto.CreateLetterRequest;
import com.rainbowletter.server.letter.application.port.in.CreateLetterCommand;
import com.rainbowletter.server.letter.application.port.in.CreateLetterUseCase;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import java.net.URI;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/letters")
@Tag(name = "letter")
class CreateLetterController {

    private final CreateLetterUseCase createLetterUseCase;

    @PostMapping
    ResponseEntity<Void> createLetter(
        @RequestParam("pet") final Long petId,
        @RequestBody final CreateLetterRequest request
    ) {
        final var command = new CreateLetterCommand(
            SecurityUtils.getEmail(),
            new PetId(petId),
            request.summary(),
            request.content(),
            request.image()
        );
        final Long id = createLetterUseCase.createLetter(command);
        return ResponseEntity.created(URI.create(id.toString())).build();
    }

}
