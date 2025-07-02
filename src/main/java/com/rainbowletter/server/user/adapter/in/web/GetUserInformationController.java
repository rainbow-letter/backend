package com.rainbowletter.server.user.adapter.in.web;

import static com.rainbowletter.server.user.application.port.in.GetUserInformationUseCase.GetUserInformationQuery;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.user.application.port.in.GetUserInformationUseCase;
import com.rainbowletter.server.user.application.port.in.dto.UserInformationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "user")
class GetUserInformationController {

    private final GetUserInformationUseCase getUserInformationUseCase;

    @GetMapping("/info")
    ResponseEntity<UserInformationResponse> getUserInformation() {
        final var query = new GetUserInformationQuery(SecurityUtils.getEmail());
        final var response = getUserInformationUseCase.getUserInformation(query);
        return ResponseEntity.ok(response);
    }

}
