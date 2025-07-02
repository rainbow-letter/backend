package com.rainbowletter.server.reply.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;
import com.rainbowletter.server.reply.application.port.in.InspectReplyUseCase;
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
class InspectReplyController {

    private final InspectReplyUseCase inspectReplyUseCase;

    @PostMapping("/inspect/{id}")
    ResponseEntity<Void> inspect(@PathVariable("id") final Long id) {
        inspectReplyUseCase.inspectReply(new ReplyId(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
