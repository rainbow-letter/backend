package com.rainbowletter.server.temporary.application.port.out;

import com.rainbowletter.server.temporary.application.domain.model.Temporary;

public interface UpdateTemporaryStatePort {

    Temporary updateTemporary(Temporary temporary);

}
