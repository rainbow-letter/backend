package com.rainbowletter.server.faq.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.faq.adapter.in.web.dto.SwitchFaqSequenceRequest;
import com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;
import com.rainbowletter.server.faq.application.port.in.SwitchFaqSequenceCommand;
import com.rainbowletter.server.faq.application.port.in.SwitchFaqSequenceUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/faqs")
@Tag(name = "faq")
class SwitchSequenceByAdminController {

    private final SwitchFaqSequenceUseCase switchFaqSequenceUseCase;

    @PutMapping("/switch-sequence")
    ResponseEntity<Void> switchSequence(@RequestBody final SwitchFaqSequenceRequest request) {
        final var command = new SwitchFaqSequenceCommand(
            new FaqId(request.id()),
            new FaqId(request.targetId())
        );
        switchFaqSequenceUseCase.switchSequence(command);
        return ResponseEntity.ok().build();
    }

}
