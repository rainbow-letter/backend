package com.rainbowletter.server.user.adapter.out.persistence;

import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.DeleteUserPort;
import com.rainbowletter.server.user.application.port.out.RegisterUserPort;
import com.rainbowletter.server.user.application.port.out.UpdateUserStatePort;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class WriteUserPersistenceAdapter implements RegisterUserPort, UpdateUserStatePort, DeleteUserPort {

    private final UserMapper userMapper;
    private final UserJpaRepository userJpaRepository;

    private User save(final User user) {
        final var mappedToJpaEntity = userMapper.mapToJpaEntity(user);
        return userMapper.mapToDomain(userJpaRepository.save(mappedToJpaEntity));
    }

    @Override
    public User registerUser(final User user) {
        return save(user);
    }

    @Override
    public void updateUser(final User user) {
        save(user);
    }

    @Override
    public void deleteAll(final List<User> users) {
        final List<Long> ids = users.stream()
            .map(user -> user.getId().value())
            .toList();
        userJpaRepository.deleteAllWithIds(ids);
    }

}
