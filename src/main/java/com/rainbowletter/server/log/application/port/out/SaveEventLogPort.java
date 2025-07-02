package com.rainbowletter.server.log.application.port.out;

import com.rainbowletter.server.log.application.domain.model.EventLog;
import java.util.List;

public interface SaveEventLogPort {

    void saveEventLog(EventLog eventLog);

    void saveEventLog(List<EventLog> eventLogs);

}
