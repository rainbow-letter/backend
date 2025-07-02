package com.rainbowletter.server.faq.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.faq.application.port.in.GetFaqsForAdminUseCase;
import com.rainbowletter.server.faq.application.port.in.dto.FaqsForAdminResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/faqs")
@Tag(name = "faq")
class GetFaqsForAdminController {

    private final GetFaqsForAdminUseCase getFaqsForAdminUseCase;

    @GetMapping
    ResponseEntity<FaqsForAdminResponse> getFaqsForAdmin() {
        final FaqsForAdminResponse responses = getFaqsForAdminUseCase.getFaqsForAdmin();
        return ResponseEntity.ok(responses);
    }

}
