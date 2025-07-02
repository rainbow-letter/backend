package com.rainbowletter.server.sharedletter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.sharedletter.adapter.in.web.dto.*;
import com.rainbowletter.server.sharedletter.application.domain.service.SharedLetterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "shared-letter", description = "무지개에 걸린 편지")
@RequiredArgsConstructor
@WebAdapter
public class SharedLetterController {

    private final SharedLetterService sharedLetterService;

    @Operation(summary = "생성")
    @PostMapping("/pets/{petId}/shared-letters")
    public ResponseEntity<SharedLetterResponse> createSharedLetter(
        @PathVariable("petId") Long petId,
        @Valid @RequestBody CreateSharedLetterRequest request
    ) {
        String email = SecurityUtils.getEmail();
        SharedLetterResponse response = sharedLetterService.createSharedLetter(petId, email, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "내가 작성한 편지 조회")
    @GetMapping("/users/@me/shared-letters")
    public ResponseEntity<List<SharedLetterResponse>> retrieveByUser(
        @Valid @ModelAttribute RetrieveSharedLetterByUserIdRequest request
    ) {
        String email = SecurityUtils.getEmail();
        final List<SharedLetterResponse> response = sharedLetterService.retrieveByUser(email, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "조회")
    @GetMapping("/shared-letters")
    public ResponseEntity<List<SharedLetterResponse>> retrieve(
        @Valid @ModelAttribute RetrieveSharedLetterRequest request
    ) {
        String email = SecurityUtils.getEmail();
        List<SharedLetterResponse> response = sharedLetterService.retrieve(email, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "샘플 편지 조회")
    @GetMapping("/shared-letters/sample")
    public ResponseEntity<List<SharedLetterResponse>> retrieveSampleLetters() {
        final List<SharedLetterResponse> response = sharedLetterService.retrieveSampleLetters();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
