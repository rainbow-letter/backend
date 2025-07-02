package com.rainbowletter.server.notification.application.port.in;

public interface SendMailUseCase {

    void sendMail(SendMailCommand command);

}
