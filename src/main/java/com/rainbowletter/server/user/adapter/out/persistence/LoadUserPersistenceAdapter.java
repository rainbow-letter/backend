package com.rainbowletter.server.user.adapter.out.persistence;

import static com.rainbowletter.server.user.application.domain.model.User.UserStatus.LEAVE;

import com.rainbowletter.server.common.annotation.PersistenceAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.domain.model.User.UserId;
import com.rainbowletter.server.user.application.port.out.ExistsUserPort;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
class LoadUserPersistenceAdapter implements ExistsUserPort, LoadUserPort {

    private final UserMapper userMapper;
    private final UserJpaRepository userJpaRepository;

    @Override
    public boolean existsUserByEmail(final String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean isLeaveEmail(final String email) {
        return userJpaRepository.existsByEmailAndStatus(email, LEAVE);
    }

    @Override
    public boolean existsUserByPhoneNumber(final String phoneNumber) {
        return userJpaRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public User loadUserByEmail(final String email) {
        return userMapper.mapToDomain(
            userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new RainbowLetterException("사용자 이메일을 찾을 수 없습니다.", email))
        );
    }

    @Override
    public User loadUserById(final UserId id) {
        return userMapper.mapToDomain(
            userJpaRepository.findById(id.value())
                .orElseThrow(() -> new RainbowLetterException("사용자를 찾을 수 없습니다.", id.value()))
        );
    }

    @Override
    public List<User> loadUsersByLeaveAndBeforeDate(final LocalDateTime beforeDate) {
        return userJpaRepository.findAllByStatusAndUpdatedAtLessThanEqual(LEAVE, beforeDate)
            .stream()
            .map(userMapper::mapToDomain)
            .toList();
    }

}
