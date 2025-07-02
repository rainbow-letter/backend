package com.rainbowletter.server.pet.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.pet.application.port.in.GetPetFromUserUseCase;
import com.rainbowletter.server.pet.application.port.in.GetPetFromUserUseCase.GetPetFromUserQuery;
import com.rainbowletter.server.pet.application.port.in.dto.PetResponse;
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
@RequestMapping("/api/pets")
@Tag(name = "pet")
class GetPetFromUserController {

    private final GetPetFromUserUseCase getPetFromUserUseCase;

    @GetMapping("/{id}")
    ResponseEntity<PetResponse> getPetFromUser(@PathVariable("id") final Long id) {
        final String email = SecurityUtils.getEmail();
        final var query = new GetPetFromUserQuery(email, new PetId(id));
        final PetResponse response = getPetFromUserUseCase.getPetFromUser(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
