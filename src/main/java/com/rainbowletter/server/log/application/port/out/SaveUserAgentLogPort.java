package com.rainbowletter.server.log.application.port.out;

import com.rainbowletter.server.log.application.domain.model.UserAgentLog;

public interface SaveUserAgentLogPort {

    UserAgentLog saveUserAgentLog(UserAgentLog userAgentLog);

}
