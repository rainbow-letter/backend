package com.rainbowletter.server.faq.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.faq.application.port.in.GetFaqsForUserUseCase;
import com.rainbowletter.server.faq.application.port.in.dto.FaqsForUserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/faqs")
@Tag(name = "faq")
class GetFaqsForUserController {

    private final GetFaqsForUserUseCase getFaqsForUserUseCase;

    @GetMapping
    ResponseEntity<FaqsForUserResponse> getFaqsForUser() {
        final FaqsForUserResponse responses = getFaqsForUserUseCase.getFaqsForUser();
        return ResponseEntity.ok(responses);
    }

}
