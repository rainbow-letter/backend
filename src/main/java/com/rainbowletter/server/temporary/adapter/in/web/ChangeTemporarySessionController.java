package com.rainbowletter.server.temporary.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.temporary.application.domain.model.Temporary.TemporaryId;
import com.rainbowletter.server.temporary.application.port.in.ChangeTemporarySessionCommand;
import com.rainbowletter.server.temporary.application.port.in.ChangeTemporarySessionUseCase;
import com.rainbowletter.server.temporary.application.port.in.dto.TemporarySessionResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/temporaries")
@Tag(name = "temporary")
class ChangeTemporarySessionController {

    private final ChangeTemporarySessionUseCase changeTemporarySessionUseCase;

    @PutMapping("/session/{id}")
    ResponseEntity<TemporarySessionResponse> changeSession(
        @PathVariable("id") final Long id) {

        final String email = SecurityUtils.getEmail();
        final var command = new ChangeTemporarySessionCommand(email, new TemporaryId(id));
        final var response = changeTemporarySessionUseCase.changeSession(command);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
