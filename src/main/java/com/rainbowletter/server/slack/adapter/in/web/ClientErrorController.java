package com.rainbowletter.server.slack.adapter.in.web;

import com.rainbowletter.server.common.annotation.WebAdapter;
import com.rainbowletter.server.common.util.SecurityUtils;
import com.rainbowletter.server.slack.adapter.in.web.dto.ClientErrorRequest;
import com.rainbowletter.server.slack.application.domain.service.SlackErrorReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client-error")
@Tag(name = "client-error", description = "클라이언트 에러 전송")
public class ClientErrorController {

    private final SlackErrorReportService slackErrorReportService;

    @Operation(summary = "에러 전송 - 슬랙")
    @PostMapping("/slack")
    public void reportClientError(@Valid @RequestBody ClientErrorRequest request) {
        String email = SecurityUtils.getEmail();
        slackErrorReportService.reportClientError(request, email);
    }

}
