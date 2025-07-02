package com.rainbowletter.server.temporary.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.temporary.adapter.in.web.dto.CreateTemporaryRequest;
import com.rainbowletter.server.temporary.application.port.in.CreateTemporaryCommand;
import com.rainbowletter.server.temporary.application.port.in.CreateTemporaryUseCase;
import com.rainbowletter.server.temporary.application.port.in.dto.TemporaryCreateResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/temporaries")
@Tag(name = "temporary")
class CreateTemporaryController {

    private final CreateTemporaryUseCase createTemporaryUseCase;

    @PostMapping
    ResponseEntity<TemporaryCreateResponse> createTemporary(
        @RequestBody final CreateTemporaryRequest request) {

        final var command = new CreateTemporaryCommand(
            SecurityUtils.getEmail(),
            new PetId(request.petId()),
            request.content()
        );
        final TemporaryCreateResponse response = createTemporaryUseCase.createTemporary(command);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
