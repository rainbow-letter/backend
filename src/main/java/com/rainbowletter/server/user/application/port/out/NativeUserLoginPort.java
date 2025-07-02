package com.rainbowletter.server.user.application.port.out;

public interface NativeUserLoginPort {

    String login(String email, String password);

}
