package com.rainbowletter.server.user.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.user.adapter.in.web.dto.ChangeUserPhoneNumberRequest;
import com.rainbowletter.server.user.application.port.in.UpdateUserPhoneNumberUseCase;
import com.rainbowletter.server.user.application.port.in.UpdateUserPhoneNumberUseCase.UpdateUserPhoneNumberCommand;
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
class ChangeUserPhoneNumberController {

    private final UpdateUserPhoneNumberUseCase updateUserPhoneNumberUseCase;

    @PutMapping("/phone-number")
    ResponseEntity<Void> changeUserPhoneNumber(
        @RequestBody final ChangeUserPhoneNumberRequest request
    ) {
        final var command = new UpdateUserPhoneNumberCommand(
            SecurityUtils.getEmail(),
            request.phoneNumber()
        );
        updateUserPhoneNumberUseCase.updateUserPhoneNumber(command);
        return ResponseEntity.ok().build();
    }

}
