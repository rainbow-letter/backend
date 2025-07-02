package com.rainbowletter.server.log.adapter.out.infrastructure;

import com.rainbowletter.server.common.annotation.PersistenceMapper;
import com.rainbowletter.server.log.application.domain.model.EventLog;
import java.util.Objects;

@PersistenceMapper
class EventLogMapper {

    EventLogJpaEntity mapToJpaEntity(final EventLog domain) {
        return new EventLogJpaEntity(
            Objects.isNull(domain.id()) ? null : domain.id().value(),
            domain.resource(),
            domain.userId(),
            domain.category(),
            domain.event(),
            domain.message(),
            domain.status()
        );
    }

}
