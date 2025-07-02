package com.rainbowletter.server.reply.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.reply.application.port.in.RegenerateReplyUseCase;
import com.rainbowletter.server.reply.application.port.in.RegenerateReplyUseCase.RegenerateReplyCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/replies")
@Tag(name = "reply")
class RegenerateReplyController {

    private final RegenerateReplyUseCase regenerateReplyUseCase;

    @PostMapping("/generate/{letterId}")
    ResponseEntity<Void> generate(@PathVariable("letterId") final Long letterId) {
        final var command = new RegenerateReplyCommand(new LetterId(letterId));
        regenerateReplyUseCase.regenerateReply(command);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
