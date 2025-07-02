package com.rainbowletter.server.temporary.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.temporary.adapter.in.web.dto.UpdateTemporaryRequest;
import com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryId;
import com.rainbowletter.server.temporary.application.port.in.UpdateTemporaryContentCommand;
import com.rainbowletter.server.temporary.application.port.in.UpdateTemporaryContentUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/temporaries")
@Tag(name = "temporary")
class UpdateTemporaryContentController {

    private final UpdateTemporaryContentUseCase updateTemporaryContentUseCase;

    @PutMapping("/{id}")
    ResponseEntity<Void> updateTemporary(
        @PathVariable("id") final Long id,
        @RequestBody final UpdateTemporaryRequest request
    ) {
        final var command = new UpdateTemporaryContentCommand(
            SecurityUtils.getEmail(),
            new TemporaryId(id),
            new PetId(request.petId()),
            request.content()
        );
        updateTemporaryContentUseCase.updateContent(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
