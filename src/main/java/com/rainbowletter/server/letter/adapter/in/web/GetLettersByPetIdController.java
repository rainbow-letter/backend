package com.rainbowletter.server.letter.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.letter.adapter.in.web.dto.LetterCollectResponse;
import com.rainbowletter.server.letter.adapter.in.web.dto.RetrieveLetterRequest;
import com.rainbowletter.server.letter.application.domain.service.GetLettersByPetIdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "letter")
@RequiredArgsConstructor
@WebAdapter
public class GetLettersByPetIdController {

    private final GetLettersByPetIdService getLettersByPetIdService;

    @Operation(summary = "펫 ID로 작성한 편지 조회")
    @GetMapping("/pets/{petId}/letters")
    public ResponseEntity<LetterCollectResponse> findByPetId(
        @PathVariable("petId") final Long petId,
        @Valid @ModelAttribute RetrieveLetterRequest query
    ) {
        final LetterCollectResponse response = getLettersByPetIdService.findByPetId(petId, query, SecurityUtils.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
