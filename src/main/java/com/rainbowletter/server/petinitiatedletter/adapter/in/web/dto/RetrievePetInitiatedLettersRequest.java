package com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto;

import com.rainbowletter.server.petinitiatedletter.application.domain.model.PetInitiatedLetterStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@ParameterObject
public record RetrievePetInitiatedLettersRequest(
    @Schema(description = "검색일 (작성일 yyyy-MM-dd)", example = "2025-02-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    LocalDate searchDate,

    @Schema(description = "발송 상태")
    PetInitiatedLetterStatus status,

    @Schema(description = "이메일 검색")
    String email
) {
}
