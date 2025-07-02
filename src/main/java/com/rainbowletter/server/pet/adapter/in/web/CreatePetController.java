package com.rainbowletter.server.pet.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.pet.adapter.in.web.dto.CreatePetRequest;
import com.rainbowletter.server.pet.application.port.in.CreatePetCommand;
import com.rainbowletter.server.pet.application.port.in.CreatePetUseCase;
import java.net.URI;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
@Tag(name = "pet")
class CreatePetController {

    private final CreatePetUseCase createPetUseCase;

    @PostMapping
    ResponseEntity<Void> createPet(@RequestBody final CreatePetRequest request) {
        final var command = new CreatePetCommand(
            SecurityUtils.getEmail(),
            request.name(),
            request.species(),
            request.owner(),
            request.personalities(),
            request.deathAnniversary(),
            request.image()
        );
        final Long id = createPetUseCase.createPet(command);
        return ResponseEntity.created(URI.create(id.toString())).build();
    }

}
