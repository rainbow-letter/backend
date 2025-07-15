package com.rainbowletter.server.user.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.user.adapter.in.web.dto.UserPetLetterSettingRequest;
import com.rainbowletter.server.user.application.domain.service.UserPetLetterSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "user", description = "회원")
public class UserPetLetterSettingController {

    private final UserPetLetterSettingService userPetLetterSettingService;

    @Operation(summary = "선편지 ON/OFF 설정")
    @PutMapping("/pet-initiated-letter/enabled")
    public void updatePetLetterEnabled(@Valid @RequestBody UserPetLetterSettingRequest request) {
        userPetLetterSettingService.updatePetLetterEnabled(SecurityUtils.getEmail(), request);
    }
}
