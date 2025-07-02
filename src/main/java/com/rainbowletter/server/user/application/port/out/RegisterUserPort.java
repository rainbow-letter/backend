package com.rainbowletter.server.user.application.port.out;

import com.rainbowletter.server.user.application.domain.model.User;

public interface RegisterUserPort {

    User registerUser(User user);

}
