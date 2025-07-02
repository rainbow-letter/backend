package com.rainbowletter.server.user.adapter.out.persistence;

import com.rainbowletter.server.common.annotation.PersistenceMapper;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import java.util.Objects;

@PersistenceMapper
class UserMapper {

    User mapToDomain(final UserJpaEntity jpaEntity) {
        return User.withId(
            new UserId(jpaEntity.getId()),
            jpaEntity.getEmail(),
            jpaEntity.getPassword(),
            jpaEntity.getPhoneNumber(),
            jpaEntity.getRole(),
            jpaEntity.getStatus(),
            jpaEntity.getProvider(),
            jpaEntity.getProviderId(),
            jpaEntity.getLastLoggedIn(),
            jpaEntity.getLastChangedPassword(),
            jpaEntity.getCreatedAt(),
            jpaEntity.getUpdatedAt()
        );
    }

    UserJpaEntity mapToJpaEntity(final User domain) {
        return new UserJpaEntity(
            Objects.isNull(domain.getId()) ? null : domain.getId().value(),
            domain.getEmail(),
            domain.getPassword(),
            domain.getPhoneNumber(),
            domain.getRole(),
            domain.getStatus(),
            domain.getProvider(),
            domain.getProviderId(),
            domain.getLastLoggedIn(),
            domain.getLastChangedPassword()
        );
    }

}
