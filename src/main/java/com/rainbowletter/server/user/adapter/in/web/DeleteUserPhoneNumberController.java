package com.rainbowletter.server.user.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.user.application.port.in.UpdateUserPhoneNumberUseCase;
import com.rainbowletter.server.user.application.port.in.UpdateUserPhoneNumberUseCase.UpdateUserPhoneNumberCommand;
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
class DeleteUserPhoneNumberController {

    private final UpdateUserPhoneNumberUseCase updateUserPhoneNumberUseCase;

    @DeleteMapping("/phone-number")
    ResponseEntity<Void> deleteUserPhoneNumber() {
        final var command = new UpdateUserPhoneNumberCommand(SecurityUtils.getEmail());
        updateUserPhoneNumberUseCase.updateUserPhoneNumber(command);
        return ResponseEntity.ok().build();
    }

}
