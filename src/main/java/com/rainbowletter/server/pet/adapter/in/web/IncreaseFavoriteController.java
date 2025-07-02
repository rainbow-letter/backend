package com.rainbowletter.server.pet.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.pet.application.port.in.IncreaseFavoriteUseCase;
import com.rainbowletter.server.pet.application.port.in.IncreaseFavoriteUseCase.IncreaseFavoriteCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
@Tag(name = "pet")
class IncreaseFavoriteController {

    private final IncreaseFavoriteUseCase increaseFavoriteUseCase;

    @PostMapping("/favorite/{id}")
    ResponseEntity<Void> increaseFavorite(@PathVariable("id") final Long id) {
        final String email = SecurityUtils.getEmail();
        final var command = new IncreaseFavoriteCommand(email, new PetId(id));
        increaseFavoriteUseCase.increaseFavorite(command);
        return ResponseEntity.ok().build();
    }

}
