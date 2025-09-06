package com.rainbowletter.server.user.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.user.adapter.in.web.dto.PetSelectionRequest;
import com.rainbowletter.server.user.adapter.in.web.dto.PetSelectionResponse;
import com.rainbowletter.server.user.adapter.in.web.dto.UserPetLetterSettingRequest;
import com.rainbowletter.server.user.application.domain.service.UserPetLetterSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/pet-initiated-letters")
@Tag(name = "user", description = "회원")
public class UserPetLetterSettingController {

    private final UserPetLetterSettingService userPetLetterSettingService;

    @Operation(summary = "선편지 ON/OFF 설정")
    @PutMapping("/enabled")
    public void updatePetLetterEnabled(@Valid @RequestBody UserPetLetterSettingRequest request) {
        userPetLetterSettingService.updatePetLetterEnabled(SecurityUtils.getEmail(), request);
    }

    @Operation(summary = "선편지 펫 등록")
    @PostMapping("/pets")
    public ResponseEntity<List<PetSelectionResponse>> registerInitiatedLetterPet(
        @Valid @RequestBody PetSelectionRequest request
    ) {
        List<PetSelectionResponse> response =
            userPetLetterSettingService.registerInitiatedLetterPet(SecurityUtils.getEmail(), request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "선편지 펫 삭제")
    @DeleteMapping("/pets")
    public ResponseEntity<List<PetSelectionResponse>> removeInitiatedLetterPet(@Valid @RequestBody PetSelectionRequest request) {
        List<PetSelectionResponse> response =
            userPetLetterSettingService.removeInitiatedLetterPet(SecurityUtils.getEmail(), request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "선편지 펫 리스트 조회")
    @GetMapping("/pets")
    public ResponseEntity<List<PetSelectionResponse>> getInitiatedLetterPets() {
        List<PetSelectionResponse> response = userPetLetterSettingService.getInitiatedLetterPets(SecurityUtils.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
