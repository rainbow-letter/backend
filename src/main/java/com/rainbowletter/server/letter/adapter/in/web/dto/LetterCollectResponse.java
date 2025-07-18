package com.rainbowletter.server.letter.adapter.in.web.dto;

import com.rainbowletter.server.letter.adapter.out.dto.PaginationInfo;
import com.rainbowletter.server.letter.application.port.in.dto.LetterSimpleResponse;
import com.rainbowletter.server.pet.application.port.in.dto.PetSummary;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterSimpleResponse;

import java.util.List;

public record LetterCollectResponse(
    PetSummary petSummary,
    List<LetterSimpleResponse> letters,
    List<PetInitiatedLetterSimpleResponse> petLetters,
    PaginationInfo paginationInfo
) {
}