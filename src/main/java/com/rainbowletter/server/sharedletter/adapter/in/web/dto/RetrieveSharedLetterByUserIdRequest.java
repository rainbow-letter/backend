package com.rainbowletter.server.sharedletter.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDateTime;

@ParameterObject
public record RetrieveSharedLetterByUserIdRequest(
    @Schema(description = "검색 시작일 (작성일)", example = "2025-02-01 00:00:00.000")
    @NotNull
    LocalDateTime startDate,

    @Schema(description = "검색 종료일 (작성일)", example = "2025-02-28 23:59:59.999")
    @NotNull
    LocalDateTime endDate
) {
}