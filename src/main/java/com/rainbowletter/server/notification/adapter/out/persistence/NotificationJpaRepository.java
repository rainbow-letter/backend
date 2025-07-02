package com.rainbowletter.server.notification.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface NotificationJpaRepository extends JpaRepository<NotificationJpaEntity, Long> {

}
