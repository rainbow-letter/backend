package com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto;

import com.rainbowletter.server.pet.application.port.in.dto.PetForAdminResponse;
import com.rainbowletter.server.user.application.port.in.dto.UserForAdminResponse;

import java.util.List;

public record PetInitiatedLetterForAdminResponse(
    UserForAdminResponse userForAdminResponse,
    Long letterCount,
    PetForAdminResponse petForAdminResponse,
    List<PetInitiatedLettersForAdminResponse> petInitiatedLettersForAdminResponse,
    PetInitiatedLetterDetailResponse petInitiatedLetterDetailResponse
) {
}
