package com.rainbowletter.server.faq.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.faq.adapter.in.web.dto.RegisterFaqRequest;
import com.rainbowletter.server.faq.application.port.in.RegisterFaqCommand;
import com.rainbowletter.server.faq.application.port.in.RegisterFaqUseCase;
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
@RequestMapping("/api/admins/faqs")
@Tag(name = "faq")
class RegisterFaqByAdminController {

    private final RegisterFaqUseCase registerFaqUseCase;

    @PostMapping
    ResponseEntity<Void> registerFaqByAdmin(@RequestBody final RegisterFaqRequest request) {
        final var command = new RegisterFaqCommand(request.summary(), request.detail());
        final Long id = registerFaqUseCase.register(command);
        return ResponseEntity.created(URI.create(id.toString())).build();
    }

}
