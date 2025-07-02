package com.rainbowletter.server.common.config;

import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class UserAgentAnalyzerConfig {

    @Bean
    UserAgentAnalyzer userAgentAnalyzer() {
        return UserAgentAnalyzer
            .newBuilder()
            .hideMatcherLoadStats()
            .withCache(10000)
            .build();
    }

}
