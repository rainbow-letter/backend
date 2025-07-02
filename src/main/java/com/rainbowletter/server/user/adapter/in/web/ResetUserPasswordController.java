package com.rainbowletter.server.user.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.user.adapter.in.web.dto.ResetUserPasswordRequest;
import com.rainbowletter.server.user.application.port.in.ResetUserPasswordUseCase;
import com.rainbowletter.server.user.application.port.in.ResetUserPasswordUseCase.ResetUserPasswordCommand;
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
class ResetUserPasswordController {

    private final ResetUserPasswordUseCase resetUserPasswordUseCase;

    @PutMapping("/reset-password")
    ResponseEntity<Void> resetUserPassword(@RequestBody final ResetUserPasswordRequest request) {
        final var command = new ResetUserPasswordCommand(
            SecurityUtils.getEmail(),
            request.newPassword()
        );
        resetUserPasswordUseCase.resetUserPassword(command);
        return ResponseEntity.ok().build();
    }

}
