package com.rainbowletter.server.user.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.user.application.port.in.LeaveUserUseCase;
import com.rainbowletter.server.user.application.port.in.LeaveUserUseCase.LeaveUserCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "user")
class LeaveUserController {

    private final LeaveUserUseCase leaveUserUseCase;

    @DeleteMapping
    public ResponseEntity<Void> leaveUser() {
        final var command = new LeaveUserCommand(SecurityUtils.getEmail());
        leaveUserUseCase.leaveUser(command);
        return ResponseEntity.ok().build();
    }

}
