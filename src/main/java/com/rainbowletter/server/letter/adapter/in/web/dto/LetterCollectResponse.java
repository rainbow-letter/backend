package com.rainbowletter.server.letter.adapter.in.web.dto;

import com.rainbowletter.server.letter.adapter.out.dto.PaginationInfo;
import com.rainbowletter.server.letter.application.port.in.dto.LetterSimpleWithSequenceResponse;
import com.rainbowletter.server.pet.application.port.in.dto.PetSummary;

import java.util.List;

public record LetterCollectResponse(
    PetSummary petSummary,
    List<LetterSimpleWithSequenceResponse> letters,
    PaginationInfo paginationInfo
) {
}