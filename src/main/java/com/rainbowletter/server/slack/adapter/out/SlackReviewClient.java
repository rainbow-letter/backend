package com.rainbowletter.server.slack.adapter.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "slackClient", url = "${slack.webhook.url.dev}")
public interface SlackReviewClient {

    @PostMapping
    void sendSlackMessage(@RequestBody Map<String, String> payload);
}