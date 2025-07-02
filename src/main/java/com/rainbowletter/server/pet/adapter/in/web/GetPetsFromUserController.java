package com.rainbowletter.server.pet.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.pet.application.port.in.GetPetsFromUserUseCase;
import com.rainbowletter.server.pet.application.port.in.GetPetsFromUserUseCase.GetPetsFromUserQuery;
import com.rainbowletter.server.pet.application.port.in.dto.PetsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
@Tag(name = "pet")
class GetPetsFromUserController {

    private final GetPetsFromUserUseCase getPetsFromUserUseCase;

    @Operation(summary = "펫 목록 조회")
    @GetMapping
    ResponseEntity<PetsResponse> getPetsFromUser() {
        final String email = SecurityUtils.getEmail();
        final var query = new GetPetsFromUserQuery(email);
        final PetsResponse responses = getPetsFromUserUseCase.getPetsFromUser(query);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

}
