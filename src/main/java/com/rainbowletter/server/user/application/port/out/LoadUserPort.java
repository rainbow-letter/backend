package com.rainbowletter.server.user.application.port.out;

import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import java.time.LocalDateTime;
import java.util.List;

public interface LoadUserPort {

    User loadUserByEmail(String email);

    User loadUserById(UserId id);

    List<User> loadUsersByLeaveAndBeforeDate(LocalDateTime beforeDate);

}
