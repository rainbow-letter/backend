package com.rainbowletter.server.slack.adapter.out;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "slackErrorClient", url = "${slack.webhook.url.error}")
public interface SlackErrorClient {

    @PostMapping
    void sendSlackMessage(@RequestBody Map<String, String> payload);
}