package com.rainbowletter.server.notification.application.port.in;

import lombok.Value;

@Value
@SuppressWarnings("ClassCanBeRecord")
public class SendMailCommand {

    String receiver;
    String sender;
    String title;
    String content;

}
