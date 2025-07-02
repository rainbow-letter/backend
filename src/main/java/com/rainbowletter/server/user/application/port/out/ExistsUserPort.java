package com.rainbowletter.server.user.application.port.out;

public interface ExistsUserPort {

    boolean existsUserByEmail(String email);

    boolean isLeaveEmail(String email);

    boolean existsUserByPhoneNumber(String phoneNumber);

}
