package com.rainbowletter.server.temporary.application.port.in;

import com.rainbowletter.server.temporary.application.port.in.dto.TemporaryCreateResponse;

public interface CreateTemporaryUseCase {

    TemporaryCreateResponse createTemporary(CreateTemporaryCommand command);

}
