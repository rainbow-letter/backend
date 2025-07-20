package com.rainbowletter.server.petinitiatedletter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}