package com.rainbowletter.server.reply.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.reply.adapter.in.web.dto.ReplyUpdateRequest;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;
import com.rainbowletter.server.reply.application.port.in.UpdateReplyCommand;
import com.rainbowletter.server.reply.application.port.in.UpdateReplyUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/replies")
@Tag(name = "reply")
class UpdateReplyController {

    private final UpdateReplyUseCase updateReplyUseCase;

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
        @PathVariable("id") final Long id,
        @RequestBody final ReplyUpdateRequest request
    ) {
        final var command = new UpdateReplyCommand(
            new ReplyId(id),
            request.promptType(),
            request.summary(),
            request.content()
        );
        updateReplyUseCase.updateReply(command);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
