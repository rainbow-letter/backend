package com.rainbowletter.server.temporary.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.temporary.application.port.in.GetTemporaryByPetUseCase;
import com.rainbowletter.server.temporary.application.port.in.GetTemporaryByPetUseCase.GetTemporaryByPetQuery;
import com.rainbowletter.server.temporary.application.port.in.dto.TemporaryResponse;
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
@RequestMapping("/api/temporaries")
@Tag(name = "temporary")
class GetTemporaryByPetController {

    private final GetTemporaryByPetUseCase getTemporaryByPetUseCase;

    @GetMapping
    ResponseEntity<TemporaryResponse> getTemporaryByPet(@RequestParam("pet") final Long petId) {
        final String email = SecurityUtils.getEmail();
        final var query = new GetTemporaryByPetQuery(email, new PetId(petId));
        final TemporaryResponse response = getTemporaryByPetUseCase.getTemporaryByPet(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
