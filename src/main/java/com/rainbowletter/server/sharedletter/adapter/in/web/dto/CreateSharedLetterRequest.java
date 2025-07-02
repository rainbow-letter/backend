package com.rainbowletter.server.sharedletter.adapter.in.web.dto;

import com.rainbowletter.server.sharedletter.application.domain.model.RecipientType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateSharedLetterRequest(
    @Schema(
        example = "엄마를 만나 행복했니? 엄마는 미키 덕분에정말 행복했어",
        description = "본문"
    )
    @NotNull
    String content,

    @Schema(
        example = "0",
        description = "내가 보낸 편지: 0, 내가 받은 편지: 1"
    )
    @NotNull
    RecipientType recipientType
) {
}