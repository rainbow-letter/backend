package com.rainbowletter.server.ai.adapter.in.web;

import com.rainbowletter.server.ai.adapter.in.web.dto.UpdateAiConfigRequest;
import com.rainbowletter.server.ai.application.port.in.UpdateAiConfigCommand;
import com.rainbowletter.server.ai.application.port.in.UpdateAiConfigUseCase;
import com.rainbowletter.server.common.annotation.WebAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/ai")
@Tag(name = "ai", description = "AI 관련")
class UpdateAiConfigController {

    private final UpdateAiConfigUseCase updateAiConfigUseCase;

    @Operation(summary = "일반 편지 프롬프트 타입/AB 테스트 설정")
    @PutMapping("/config")
    void updateConfig(@RequestBody final UpdateAiConfigRequest request) {
        final var command = new UpdateAiConfigCommand(request.useABTest(), request.selectPrompt());
        updateAiConfigUseCase.updateConfig(command);
    }

    @Operation(summary = "선편지 프롬프트 타입/AB 테스트 설정")
    @PutMapping("/config/pet-initiated-letter")
    void updatePetInitiatedLetterConfig(@RequestBody final UpdateAiConfigRequest request) {
        final var command = new UpdateAiConfigCommand(request.useABTest(), request.selectPrompt());
        updateAiConfigUseCase.updatePetInitiatedLetterConfig(command);
    }

}
