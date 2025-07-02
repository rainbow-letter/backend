package com.rainbowletter.server.user.adapter.in.web;

import static com.rainbowletter.server.user.application.port.in.UserRegisterUseCase.UserRegisterCommand;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.user.adapter.in.web.dto.UserRegisterRequest;
import com.rainbowletter.server.user.application.port.in.UserRegisterUseCase;
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
@RequestMapping("/api/users")
@Tag(name = "user")
class UserRegisterController {

    private final UserRegisterUseCase userRegisterUseCase;

    @PostMapping("/create")
    ResponseEntity<Void> registerUser(@RequestBody final UserRegisterRequest request) {
        final var command = new UserRegisterCommand(request.email(), request.password());
        final Long id = userRegisterUseCase.registerUser(command);
        return ResponseEntity.created(URI.create(id.toString())).build();
    }

}
