package com.rainbowletter.server.ai.adapter.in.web;

import com.rainbowletter.server.ai.application.port.in.GetAiSettingUseCase;
import com.rainbowletter.server.ai.application.port.in.dto.AiSettingResponse;
import com.rainbowletter.server.common.annotation.WebAdapter;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "ai", description = "AI 관련")
class GetAiSettingController {

    private final GetAiSettingUseCase getAiSettingUseCase;

    @Operation(summary = "일반 편지 프롬프트 옵션 조회")
    @GetMapping("/setting")
    ResponseEntity<AiSettingResponse> getSetting() {
        final AiSettingResponse response = getAiSettingUseCase.getSetting();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "선편지 프롬프트 옵션 조회")
    @GetMapping("/setting/pet-initiated-letter")
    ResponseEntity<AiSettingResponse> getPetInitiatedLetterSetting() {
        final AiSettingResponse response = getAiSettingUseCase.getPetInitiatedLetterSetting();
        return ResponseEntity.ok(response);
    }

}
