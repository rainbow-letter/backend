package com.rainbowletter.server.temporary.application.port.out;

import com.rainbowletter.server.temporary.application.domain.model.Temporary;

public interface SaveTemporaryPort {

    Temporary save(Temporary temporary);

}
