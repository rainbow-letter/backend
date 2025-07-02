package com.rainbowletter.server.user.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.user.adapter.in.web.dto.ChangeUserPasswordRequest;
import com.rainbowletter.server.user.application.port.in.ChangeUserPasswordUseCase;
import com.rainbowletter.server.user.application.port.in.ChangeUserPasswordUseCase.ChangeUserPasswordCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "user")
class ChangeUserPasswordController {

    private final ChangeUserPasswordUseCase changeUserPasswordUseCase;

    @PutMapping("/password")
    ResponseEntity<Void> changeUserPassword(@RequestBody final ChangeUserPasswordRequest request) {
        final var command = new ChangeUserPasswordCommand(
            SecurityUtils.getEmail(),
            request.password(),
            request.newPassword()
        );
        changeUserPasswordUseCase.changeUserPassword(command);
        return ResponseEntity.ok().build();
    }

}
