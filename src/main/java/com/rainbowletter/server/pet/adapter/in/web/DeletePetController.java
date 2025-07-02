package com.rainbowletter.server.pet.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.pet.application.port.in.DeletePetUseCase;
import com.rainbowletter.server.pet.application.port.in.DeletePetUseCase.DeletePetByIdCommand;
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
@RequestMapping("/api/pets")
@Tag(name = "pet")
class DeletePetController {

    private final DeletePetUseCase deletePetUseCase;

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        final String email = SecurityUtils.getEmail();
        final var command = new DeletePetByIdCommand(email, new PetId(id));
        deletePetUseCase.deletePet(command);
        return ResponseEntity.ok().build();
    }

}
