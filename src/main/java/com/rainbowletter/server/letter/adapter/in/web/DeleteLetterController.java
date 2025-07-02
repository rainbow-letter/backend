package com.rainbowletter.server.letter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.letter.application.port.in.DeleteLetterUseCase;
import com.rainbowletter.server.letter.application.port.in.DeleteLetterUseCase.DeleteLetterCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/letters")
@Tag(name = "letter")
class DeleteLetterController {

    private final DeleteLetterUseCase deleteLetterUseCase;

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteLetter(@PathVariable("id") final Long id) {
        final var command = new DeleteLetterCommand(SecurityUtils.getEmail(), new LetterId(id));
        deleteLetterUseCase.deleteLetter(command);
        return ResponseEntity.ok().build();
    }

}
