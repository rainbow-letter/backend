package com.rainbowletter.server.reply.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;
import com.rainbowletter.server.reply.application.port.in.ReadReplyUseCase;
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
@RequestMapping("/api/replies")
@Tag(name = "reply")
class ReadReplyController {

    private final ReadReplyUseCase readReplyUseCase;

    @PostMapping("/read/{id}")
    ResponseEntity<Void> read(@PathVariable("id") final Long id) {
        readReplyUseCase.readReply(new ReplyId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
