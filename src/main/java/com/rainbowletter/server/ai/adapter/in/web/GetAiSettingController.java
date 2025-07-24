package com.rainbowletter.server.ai.adapter.in.web;

import com.rainbowletter.server.ai.application.port.in.GetAiSettingUseCase;
import com.rainbowletter.server.ai.application.port.in.dto.AiSettingResponse;
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
class GetAiSettingController {

    private final GetAiSettingUseCase getAiSettingUseCase;

    @GetMapping("/setting")
    ResponseEntity<AiSettingResponse> getSetting() {
        final AiSettingResponse response = getAiSettingUseCase.getSetting();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/setting/pet-initiated-letter")
    ResponseEntity<AiSettingResponse> getPetInitiatedLetterSetting() {
        final AiSettingResponse response = getAiSettingUseCase.getPetInitiatedLetterSetting();
        return ResponseEntity.ok(response);
    }

}
