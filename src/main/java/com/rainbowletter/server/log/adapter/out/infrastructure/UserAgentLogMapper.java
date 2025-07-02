package com.rainbowletter.server.log.adapter.out.infrastructure;

import com.rainbowletter.server.common.annotation.PersistenceMapper;
import com.rainbowletter.server.log.application.domain.model.UserAgent;
import com.rainbowletter.server.log.application.domain.model.UserAgentLog;
import com.rainbowletter.server.log.application.domain.model.UserAgentLog.UserAgentLogId;
import java.util.Objects;

@PersistenceMapper
class UserAgentLogMapper {

    UserAgentLog mapToDomain(final UserAgentLogJpaEntity jpaEntity) {
        final UserAgent userAgent = new UserAgent(
            jpaEntity.getDevice(),
            jpaEntity.getDeviceName(),
            jpaEntity.getOs(),
            jpaEntity.getOsName(),
            jpaEntity.getOsVersion(),
            jpaEntity.getAgent(),
            jpaEntity.getAgentName(),
            jpaEntity.getAgentVersion()
        );
        return UserAgentLog.withId(
            new UserAgentLogId(jpaEntity.getId()),
            jpaEntity.getEvent(),
            userAgent
        );
    }

    UserAgentLogJpaEntity mapToJpaEntity(final UserAgentLog domain) {
        return new UserAgentLogJpaEntity(
            Objects.isNull(domain.id()) ? null : domain.id().value(),
            domain.event(),
            domain.device(),
            domain.deviceName(),
            domain.os(),
            domain.osName(),
            domain.osVersion(),
            domain.agent(),
            domain.agentName(),
            domain.agentVersion()
        );
    }

}
