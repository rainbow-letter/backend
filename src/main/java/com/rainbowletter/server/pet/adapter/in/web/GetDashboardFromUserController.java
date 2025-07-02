package com.rainbowletter.server.pet.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.pet.application.port.in.GetDashboardFromUserUseCase;
import com.rainbowletter.server.pet.application.port.in.GetDashboardFromUserUseCase.GetDashboardFromUserQuery;
import com.rainbowletter.server.pet.application.port.in.dto.PetDashboardResponses;
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
class GetDashboardFromUserController {

    private final GetDashboardFromUserUseCase getDashboardFromUserUseCase;

    @GetMapping("/dashboard")
    ResponseEntity<PetDashboardResponses> getDashboardFromUser() {
        final String email = SecurityUtils.getEmail();
        final GetDashboardFromUserQuery query = new GetDashboardFromUserQuery(email);
        final var responses = getDashboardFromUserUseCase.getDashboardFromUser(query);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

}
