package com.rainbowletter.server.notification.application.port.out;

import com.rainbowletter.server.notification.application.domain.model.Notification;

public interface SaveNotificationPort {

    void saveNotification(Notification notification);

}
