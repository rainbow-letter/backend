package com.rainbowletter.server.common.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
public class ClientConfig {

    @Value("${client.url}")
    private List<String> urls;

    public String getBaseUrl() {
        if (urls.isEmpty()) {
            throw new IllegalStateException("Required initialize client url");
        }
        return urls.get(0);
    }

}