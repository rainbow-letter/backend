package com.rainbowletter.server.letter.adapter.in.web;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.letter.application.port.in.GetLettersFromAdminQuery;
import com.rainbowletter.server.letter.application.port.in.GetLettersFromAdminUseCase;
import com.rainbowletter.server.letter.application.port.in.dto.LetterAdminPageResponse;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins/letters")
@Tag(name = "letter")
class GetLettersFromAdminPageController {

    private final GetLettersFromAdminUseCase getLettersFromAdminUseCase;

    @GetMapping("/list")
    ResponseEntity<Page<LetterAdminPageResponse>> getLettersFromAdminPage(
        @RequestParam(value = "start") final LocalDate start,
        @RequestParam(value = "end") final LocalDate end,
        @RequestParam(value = "status", required = false) final ReplyStatus status,
        @RequestParam(value = "email", required = false) final String email,
        @RequestParam(value = "inspect", required = false) final Boolean inspect,
        @PageableDefault(sort = "createdAt", direction = DESC) final Pageable pageable
    ) {
        final var query = new GetLettersFromAdminQuery(
            start,
            end,
            status,
            email,
            inspect,
            pageable
        );
        final Page<LetterAdminPageResponse> response = getLettersFromAdminUseCase.getLetters(query);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
