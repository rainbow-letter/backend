package com.rainbowletter.server.user.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.user.application.port.in.dto.UserVerifyResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "user")
class VerifyUserTokenController {

    @GetMapping("/verify")
    ResponseEntity<UserVerifyResponse> verifyUserToken() {
        final String email = SecurityUtils.getEmail();
        final String role = SecurityUtils.getRole();
        return ResponseEntity.ok(UserVerifyResponse.of(email, role));
    }

}
