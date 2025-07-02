package com.rainbowletter.server.log.adapter.out.infrastructure;

import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.log.application.domain.model.EventLog;
import com.rainbowletter.server.log.application.port.out.SaveEventLogPort;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class EventLogPersistenceAdapter implements SaveEventLogPort {

    private final EventLogMapper eventLogMapper;
    private final EventLogJpaRepository eventLogJpaRepository;

    @Override
    public void saveEventLog(final EventLog eventLog) {
        final var mappedToJpaEntity = eventLogMapper.mapToJpaEntity(eventLog);
        eventLogJpaRepository.save(mappedToJpaEntity);
    }

    @Override
    public void saveEventLog(final List<EventLog> eventLogs) {
        final var mappedToJpaEntities = eventLogs.stream()
            .map(eventLogMapper::mapToJpaEntity)
            .toList();
        eventLogJpaRepository.saveAll(mappedToJpaEntities);
    }

}
