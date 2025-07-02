package com.rainbowletter.server.faq.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.faq.application.domain.model.Faq.FaqId;
import com.rainbowletter.server.faq.application.port.in.DeleteFaqUseCase;
import com.rainbowletter.server.faq.application.port.in.DeleteFaqUseCase.DeleteFaqCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/faqs")
@Tag(name = "faq")
class DeleteFaqByAdminController {

    private final DeleteFaqUseCase deleteFaqUseCase;

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        final var command = new DeleteFaqCommand(new FaqId(id));
        deleteFaqUseCase.delete(command);
        return ResponseEntity.ok().build();
    }

}
