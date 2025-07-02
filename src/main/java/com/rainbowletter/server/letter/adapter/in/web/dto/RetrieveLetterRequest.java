package com.rainbowletter.server.letter.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@ParameterObject
public record RetrieveLetterRequest(
    @Schema(
        example = "10",
        description = "이전페이지 마지막 ID (첫페이지 요청시 미입력)"
    )
    Long after,

    @Schema(
        example = "10",
        defaultValue = "10",
        description = "한페이지 리소스 수 (최대 1000)"
    )
    @Min(1)
    @Max(1000)
    @NotNull
    int limit,

    @Schema(
        example = "2025-02-01 00:00:00.000",
        description = "검색 시작일시"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @NotNull
    LocalDateTime startDate,

    @Schema(
        example = "2025-02-01 00:00:00.000",
        description = "검색 종료일시"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @NotNull
    LocalDateTime endDate
) {
}
