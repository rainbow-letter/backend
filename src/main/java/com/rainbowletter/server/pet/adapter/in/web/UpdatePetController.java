package com.rainbowletter.server.pet.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.pet.adapter.in.web.dto.UpdatePetRequest;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.pet.application.port.in.UpdatePetCommand;
import com.rainbowletter.server.pet.application.port.in.UpdatePetUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
@Tag(name = "pet")
class UpdatePetController {

    private final UpdatePetUseCase updatePetUseCase;

    @PutMapping("/{id}")
    ResponseEntity<Void> updatePet(
        @PathVariable("id") final Long id,
        @RequestBody final UpdatePetRequest request
    ) {
        final var command = new UpdatePetCommand(
            new PetId(id),
            SecurityUtils.getEmail(),
            request.name(),
            request.species(),
            request.owner(),
            request.personalities(),
            request.deathAnniversary(),
            request.image()
        );
        updatePetUseCase.updatePet(command);
        return ResponseEntity.ok().build();
    }

}
