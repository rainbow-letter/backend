package com.rainbowletter.server.notification.adapter.out.persistence;

import com.rainbowletter.server.common.annotation.PersistenceMapper;
import com.rainbowletter.server.notification.application.domain.model.Notification;
import java.util.Objects;

@PersistenceMapper
class NotificationMapper {

    NotificationJpaEntity mapToJpaEntity(final Notification domain) {
        return new NotificationJpaEntity(
            Objects.isNull(domain.getId()) ? null : domain.getId().value(),
            domain.getTitle(),
            domain.getContent(),
            domain.getReceiver(),
            domain.getSender(),
            domain.getType(),
            domain.getCode(),
            domain.getStatusMessage()
        );
    }

}
