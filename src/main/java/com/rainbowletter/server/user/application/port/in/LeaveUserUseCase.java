package com.rainbowletter.server.user.application.port.in;

public interface LeaveUserUseCase {

    void leaveUser(final LeaveUserCommand command);

    record LeaveUserCommand(String email) {

    }

}
