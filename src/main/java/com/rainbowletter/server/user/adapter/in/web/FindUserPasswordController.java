package com.rainbowletter.server.user.adapter.in.web;

import static com.rainbowletter.server.user.application.port.in.FindUserPasswordUseCase.FindUserPasswordQuery;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.user.adapter.in.web.dto.FindUserPasswordRequest;
import com.rainbowletter.server.user.application.port.in.FindUserPasswordUseCase;
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
@RequestMapping("/api/users")
@Tag(name = "user")
class FindUserPasswordController {

    private final FindUserPasswordUseCase findUserPasswordUseCase;

    @PostMapping("/find-password")
    ResponseEntity<Void> findUserPassword(@RequestBody final FindUserPasswordRequest request) {
        final var query = new FindUserPasswordQuery(request.email());
        findUserPasswordUseCase.findUserPassword(query);
        return ResponseEntity.ok().build();
    }

}
