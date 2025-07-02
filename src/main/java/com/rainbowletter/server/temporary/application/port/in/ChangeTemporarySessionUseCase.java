package com.rainbowletter.server.temporary.application.port.in;

import com.rainbowletter.server.temporary.application.port.in.dto.TemporarySessionResponse;

public interface ChangeTemporarySessionUseCase {

    TemporarySessionResponse changeSession(ChangeTemporarySessionCommand command);

}
