package com.rainbowletter.server.user.application.port.out;

import com.rainbowletter.server.user.application.domain.model.User;
import java.util.List;

public interface DeleteUserPort {

    void deleteAll(List<User> users);

}
