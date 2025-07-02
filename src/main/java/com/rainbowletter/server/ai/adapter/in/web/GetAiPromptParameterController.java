package com.rainbowletter.server.ai.adapter.in.web;

import com.rainbowletter.server.ai.application.port.in.GetAiPromptParameterUseCase;
import com.rainbowletter.server.ai.application.port.in.dto.AiPromptParameterResponse;
import com.rainbowletter.server.common.annotation.WebAdapter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/ai")
@Tag(name = "ai")
class GetAiPromptParameterController {

    private final GetAiPromptParameterUseCase getAiPromptParameterUseCase;

    @GetMapping("/parameters")
    ResponseEntity<AiPromptParameterResponse> getParameters() {
        final AiPromptParameterResponse response = getAiPromptParameterUseCase.getPromptParameters();
        return ResponseEntity.ok(response);
    }

}
