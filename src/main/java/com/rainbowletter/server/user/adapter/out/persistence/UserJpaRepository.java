package com.rainbowletter.server.user.adapter.out.persistence;

import com.rainbowletter.server.user.application.domain.model.User.UserStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndStatus(String email, UserStatus status);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<UserJpaEntity> findByEmail(String email);

    List<UserJpaEntity> findAllByStatusAndUpdatedAtLessThanEqual(
        UserStatus status,
        LocalDateTime beforeDate
    );

    @Modifying
    @Query("DELETE FROM UserJpaEntity user WHERE user.id IN ?1")
    void deleteAllWithIds(List<Long> ids);

}
