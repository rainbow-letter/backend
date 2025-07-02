package com.rainbowletter.server.faq.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.faq.adapter.in.web.dto.UpdateFaqContentRequest;
import com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;
import com.rainbowletter.server.faq.application.port.in.UpdateFaqContentCommand;
import com.rainbowletter.server.faq.application.port.in.UpdateFaqContentUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/faqs")
@Tag(name = "faq")
class UpdateFaqByAdminController {

    private final UpdateFaqContentUseCase updateFaqContentUseCase;

    @PutMapping("/{id}")
    ResponseEntity<Void> update(
        @PathVariable("id") final Long id,
        @RequestBody final UpdateFaqContentRequest request
    ) {
        final var command = new UpdateFaqContentCommand(
            new FaqId(id),
            request.summary(),
            request.detail()
        );
        updateFaqContentUseCase.updateContent(command);
        return ResponseEntity.ok().build();
    }

}
