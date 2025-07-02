package com.rainbowletter.server.temporary.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryId;
import com.rainbowletter.server.temporary.application.port.in.DeleteTemporaryCommand;
import com.rainbowletter.server.temporary.application.port.in.DeleteTemporaryUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/temporaries")
@Tag(name = "temporary")
class DeleteTemporaryController {

    private final DeleteTemporaryUseCase deleteTemporaryUseCase;

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
        @PathVariable("id") final Long id,
        @RequestParam("pet") final Long petId
    ) {
        final var command = new DeleteTemporaryCommand(
            SecurityUtils.getEmail(),
            new TemporaryId(id),
            new PetId(petId)
        );
        deleteTemporaryUseCase.deleteTemporary(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
