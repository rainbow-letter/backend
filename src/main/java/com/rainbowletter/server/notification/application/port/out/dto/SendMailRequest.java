package com.rainbowletter.server.notification.application.port.out.dto;

public record SendMailRequest(String receiver, String title, String content) {

}
