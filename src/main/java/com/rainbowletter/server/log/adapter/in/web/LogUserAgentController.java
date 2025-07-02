package com.rainbowletter.server.log.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.log.adapter.in.web.dto.LogUserAgentRequest;
import com.rainbowletter.server.log.application.port.in.LogUserAgentCommand;
import com.rainbowletter.server.log.application.port.in.LogUserAgentUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
@Tag(name = "log")
class LogUserAgentController {

    private final LogUserAgentUseCase logUserAgentUseCase;

    @PostMapping
    ResponseEntity<Void> logUserAgent(
        final HttpServletRequest servletRequest,
        @RequestBody final LogUserAgentRequest request
    ) {
        final String userAgent = servletRequest.getHeader("USER-AGENT");
        final var command = new LogUserAgentCommand(userAgent, request.event());
        final Long id = logUserAgentUseCase.logUserAgent(command);
        return ResponseEntity.created(URI.create(id.toString())).build();
    }

}
