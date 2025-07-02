package com.rainbowletter.server.log.application.port.out;

import com.rainbowletter.server.log.application.domain.model.UserAgent;

public interface AnalyzeUserAgentPort {

    UserAgent analyzeUserAgent(String userAgent);

}
