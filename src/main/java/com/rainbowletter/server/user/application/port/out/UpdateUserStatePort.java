package com.rainbowletter.server.user.application.port.out;

import com.rainbowletter.server.user.application.domain.model.User;

public interface UpdateUserStatePort {

    void updateUser(User user);

}
