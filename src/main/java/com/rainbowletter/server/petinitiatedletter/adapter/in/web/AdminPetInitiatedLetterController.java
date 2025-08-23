package com.rainbowletter.server.petinitiatedletter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterForAdminResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterResponse;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterUpdateRequest;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.RetrievePetInitiatedLettersRequest;
import com.rainbowletter.server.petinitiatedletter.application.domain.service.PetInitiatedLetterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/pet-initiated-letters")
@Tag(name = "pet-initiated-letter", description = "선편지")
public class AdminPetInitiatedLetterController {

    private final PetInitiatedLetterService petInitiatedLetterService;

    @Operation(summary = "선편지 목록 조회/검색")
    @GetMapping
    public ResponseEntity<Page<PetInitiatedLetterResponse>> getPetInitiatedLetters(
        @Valid @ModelAttribute RetrievePetInitiatedLettersRequest request,
        @ParameterObject Pageable pageable
    ) {
        Page<PetInitiatedLetterResponse> response = petInitiatedLetterService.getPetInitiatedLetters(request, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "관리자 - 선편지 상세 조회")
    @GetMapping("/{letter-id}")
    public ResponseEntity<PetInitiatedLetterForAdminResponse> getPetInitiatedLetterDetailForAdmin(
        @PathVariable("letter-id") Long letterId,
        @RequestParam("user-id") Long userId,
        @RequestParam("pet-id") Long petId
    ) {
        PetInitiatedLetterForAdminResponse response =
            petInitiatedLetterService.getPetInitiatedLetterDetailForAdmin(letterId, userId, petId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "선편지 수정")
    @PutMapping("/{letter-id}")
    public void updatePetInitiatedLetter(
        @PathVariable("letter-id") Long letterId,
        @Valid @RequestBody PetInitiatedLetterUpdateRequest request
    ) {
        petInitiatedLetterService.updatePetInitiatedLetter(letterId, request);
    }

    @Operation(summary = "선편지 GPT 재생성")
    @PostMapping("/generate/{letter-id}")
    public void generate(@PathVariable("letter-id") Long letterId) {
        petInitiatedLetterService.regeneratePetInitiatedLetter(letterId);
    }

    @Operation(summary = "선편지 발송")
    @PostMapping("/submit/{letter-id}")
    public void submit(@PathVariable("letter-id") Long letterId) {
        petInitiatedLetterService.submitPetInitiatedLetter(letterId);
    }

}