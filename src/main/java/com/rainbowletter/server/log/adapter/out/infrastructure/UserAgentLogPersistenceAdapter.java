package com.rainbowletter.server.log.adapter.out.infrastructure;

import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.log.application.domain.model.UserAgentLog;
import com.rainbowletter.server.log.application.port.out.SaveUserAgentLogPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class UserAgentLogPersistenceAdapter implements SaveUserAgentLogPort {

    private final UserAgentLogMapper userAgentLogMapper;
    private final UserAgentLogJpaRepository userAgentLogJpaRepository;

    @Override
    public UserAgentLog saveUserAgentLog(final UserAgentLog userAgentLog) {
        final var mappedToJpaEntity = userAgentLogMapper.mapToJpaEntity(userAgentLog);
        final var userAgentLogJpaEntity = userAgentLogJpaRepository.save(mappedToJpaEntity);
        return userAgentLogMapper.mapToDomain(userAgentLogJpaEntity);
    }

}
