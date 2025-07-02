package com.rainbowletter.server.log.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.log.application.domain.model.UserAgent;
import com.rainbowletter.server.log.application.domain.model.UserAgentLog;
import com.rainbowletter.server.log.application.port.in.LogUserAgentCommand;
import com.rainbowletter.server.log.application.port.in.LogUserAgentUseCase;
import com.rainbowletter.server.log.application.port.out.AnalyzeUserAgentPort;
import com.rainbowletter.server.log.application.port.out.SaveUserAgentLogPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class LogUserAgentService implements LogUserAgentUseCase {

    private final AnalyzeUserAgentPort analyzeUserAgentPort;
    private final SaveUserAgentLogPort saveUserAgentLogPort;

    @Override
    public Long logUserAgent(final LogUserAgentCommand command) {
        final UserAgent userAgent = analyzeUserAgentPort.analyzeUserAgent(command.getUserAgent());
        final UserAgentLog userAgentLog = UserAgentLog.withoutId(command.getEvent(), userAgent);
        return saveUserAgentLogPort.saveUserAgentLog(userAgentLog)
            .id()
            .value();
    }

}
