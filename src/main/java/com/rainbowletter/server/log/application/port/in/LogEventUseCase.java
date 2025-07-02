package com.rainbowletter.server.log.application.port.in;

import java.util.List;

public interface LogEventUseCase {

    void successLog(LogEventCommand command);

    void successLog(List<LogEventCommand> commands);

    void failLog(LogEventCommand command);

    void failLog(List<LogEventCommand> commands);

}
