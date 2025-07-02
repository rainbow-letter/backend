package com.rainbowletter.server.notification.adapter.out.persistence;

import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.notification.application.domain.model.Notification;
import com.rainbowletter.server.notification.application.port.out.SaveNotificationPort;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements SaveNotificationPort {

    private final NotificationMapper notificationMapper;
    private final NotificationJpaRepository notificationJpaRepository;

    @Override
    public void saveNotification(final Notification notification) {
        final var mappedToJpaEntity = notificationMapper.mapToJpaEntity(notification);
        notificationJpaRepository.save(mappedToJpaEntity);
    }

}
