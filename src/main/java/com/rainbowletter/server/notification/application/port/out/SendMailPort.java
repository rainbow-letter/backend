package com.rainbowletter.server.notification.application.port.out;

import com.rainbowletter.server.notification.application.port.out.dto.SendMailRequest;

public interface SendMailPort {

    void sendMail(SendMailRequest request);

}
