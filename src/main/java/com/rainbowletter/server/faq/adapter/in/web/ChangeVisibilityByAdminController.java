package com.rainbowletter.server.faq.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;
import com.rainbowletter.server.faq.application.port.in.ChangeFaqVisibilityUseCase;
import com.rainbowletter.server.faq.application.port.in.ChangeFaqVisibilityUseCase.ChangeFaqVisibilityCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/faqs")
@Tag(name = "faq")
class ChangeVisibilityByAdminController {

    private final ChangeFaqVisibilityUseCase changeFaqVisibilityUseCase;

    @PutMapping("/visibility/{id}")
    ResponseEntity<Void> changeVisibility(@PathVariable("id") final Long id) {
        final var command = new ChangeFaqVisibilityCommand(new FaqId(id));
        changeFaqVisibilityUseCase.changeVisibility(command);
        return ResponseEntity.ok().build();
    }

}
