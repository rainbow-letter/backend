package com.rainbowletter.server.ai.adapter.in.web;

import static com.rainbowletter.server.ai.application.domain.model.AiOption.AiOptionId;

import com.rainbowletter.server.ai.adapter.in.web.dto.UpdateAiOptionRequest;
import com.rainbowletter.server.ai.application.port.in.UpdateAiOptionCommand;
import com.rainbowletter.server.ai.application.port.in.UpdateAiOptionUseCase;
import com.rainbowletter.server.common.annotation.WebAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/ai")
@Tag(name = "ai", description = "AI 관련")
class UpdateAiOptionController {

    private final UpdateAiOptionUseCase updateAiOptionUseCase;

    @Operation(summary = "프롬프트 옵션 수정")
    @PutMapping("/options/{id}")
    void updatePrompt(
        @PathVariable("id") final Long id,
        @RequestBody final UpdateAiOptionRequest request
    ) {
        final var command = new UpdateAiOptionCommand(
            new AiOptionId(id),
            request.maxTokens(),
            request.temperature(),
            request.topP(),
            request.frequencyPenalty(),
            request.presencePenalty(),
            request.stop()
        );
        updateAiOptionUseCase.updateOption(command);
    }

}
