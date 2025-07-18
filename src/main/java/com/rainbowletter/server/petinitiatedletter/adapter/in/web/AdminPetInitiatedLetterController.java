package com.rainbowletter.server.petinitiatedletter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.petinitiatedletter.adapter.in.web.dto.PetInitiatedLetterResponse;
import com.rainbowletter.server.petinitiatedletter.application.domain.service.PetInitiatedLetterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/pet-initiated-letters")
@Tag(name = "pet-initiated-letter", description = "선편지")
public class AdminPetInitiatedLetterController {

    private final PetInitiatedLetterService petInitiatedLetterService;

    @Operation(summary = "선편지 리스트 조회")
    @GetMapping
    public ResponseEntity<List<PetInitiatedLetterResponse>> getPetInitiatedLetters() {
        List<PetInitiatedLetterResponse> response = petInitiatedLetterService.getPetInitiatedLetters(SecurityUtils.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}