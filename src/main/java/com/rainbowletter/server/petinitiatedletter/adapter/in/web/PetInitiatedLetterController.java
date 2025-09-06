package com.rainbowletter.server.petinitiatedletter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterSummary;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterWithPetNameResponse;
import com.rainbowletter.server.petinitiatedletter.application.domain.service.PetInitiatedLetterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pet-initiated-letters")
@Tag(name = "pet-initiated-letter", description = "선편지")
public class PetInitiatedLetterController {

    private final PetInitiatedLetterService petInitiatedLetterService;

    @Operation(summary = "공유링크 - 선편지 상세 조회")
    @GetMapping("/share/{shareLink}")
    public ResponseEntity<PetInitiatedLetterWithPetNameResponse> getLetterByShareLink(
        @PathVariable("shareLink") String shareLink
    ) {
        PetInitiatedLetterWithPetNameResponse response =
            petInitiatedLetterService.getLetterByShareLink(UUID.fromString(shareLink));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "사용자 - 선편지 상세 조회")
    @GetMapping("/{letter-id}")
    public ResponseEntity<PetInitiatedLetterSummary> getPetInitiatedLetterDetail(
        @PathVariable("letter-id") Long letterId
    ) {
        String email = SecurityUtils.getEmail();
        PetInitiatedLetterSummary response = petInitiatedLetterService.getPetInitiatedLetterDetail(email, letterId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
