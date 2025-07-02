package com.rainbowletter.server.log.application.domain.service;

import com.rainbowletter.server.common.annotation.UseCase;
import com.rainbowletter.server.log.application.domain.model.EventLog;
import com.rainbowletter.server.log.application.domain.model.EventLog.EventLogStatus;
import com.rainbowletter.server.log.application.port.in.LogEventCommand;
import com.rainbowletter.server.log.application.port.in.LogEventUseCase;
import com.rainbowletter.server.log.application.port.out.SaveEventLogPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class LogEventService implements LogEventUseCase {

    private final SaveEventLogPort saveEventLogPort;

    @Override
    public void successLog(final LogEventCommand command) {
        final EventLog eventLog = EventLog.withoutId(
            command.getResource(),
            command.getUserId(),
            command.getCategory(),
            command.getEvent(),
            command.getMessage(),
            EventLogStatus.SUCCESS
        );
        saveEventLogPort.saveEventLog(eventLog);
    }

    @Override
    public void successLog(final List<LogEventCommand> commands) {
        final List<EventLog> eventLogs = commands.stream()
            .map(command -> EventLog.withoutId(
                command.getResource(),
                command.getUserId(),
                command.getCategory(),
                command.getEvent(),
                command.getMessage(),
                EventLogStatus.SUCCESS
            ))
            .toList();
        saveEventLogPort.saveEventLog(eventLogs);
    }

    @Override
    public void failLog(final LogEventCommand command) {
        final EventLog eventLog = EventLog.withoutId(
            command.getResource(),
            command.getUserId(),
            command.getCategory(),
            command.getEvent(),
            command.getMessage(),
            EventLogStatus.FAIL
        );
        saveEventLogPort.saveEventLog(eventLog);
    }

    @Override
    public void failLog(final List<LogEventCommand> commands) {
        final List<EventLog> eventLogs = commands.stream()
            .map(command -> EventLog.withoutId(
                command.getResource(),
                command.getUserId(),
                command.getCategory(),
                command.getEvent(),
                command.getMessage(),
                EventLogStatus.FAIL
            ))
            .toList();
        saveEventLogPort.saveEventLog(eventLogs);
    }

}
