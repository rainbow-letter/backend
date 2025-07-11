package com.rainbowletter.server.user.application.domain.model;

import com.rainbowletter.server.common.application.domain.model.AggregateRoot;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends AggregateRoot {

    private final UserId id;
    private final String email;

    private String password;
    private String phoneNumber;
    private UserRole role;
    private UserStatus status;
    private OAuthProvider provider;
    private String providerId;
    private LocalDateTime lastLoggedIn;
    private LocalDateTime lastChangedPassword;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean petInitiatedLetterEnabled;

    @SuppressWarnings("java:S107")
    public static User withId(
        final UserId id,
        final String email,
        final String password,
        final String phoneNumber,
        final UserRole role,
        final UserStatus status,
        final OAuthProvider provider,
        final String providerId,
        final LocalDateTime lastLoggedIn,
        final LocalDateTime lastChangedPassword,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final boolean petInitiatedLetterEnabled
    ) {
        return new User(
            id,
            email,
            password,
            phoneNumber,
            role,
            status,
            provider,
            providerId,
            lastLoggedIn,
            lastChangedPassword,
            createdAt,
            updatedAt,
            petInitiatedLetterEnabled
        );
    }

    @SuppressWarnings("java:S107")
    public static User withoutId(
        final String email,
        final String password,
        final String phoneNumber,
        final UserRole role,
        final UserStatus status,
        final OAuthProvider provider,
        final String providerId,
        final LocalDateTime lastLoggedIn,
        final LocalDateTime lastChangedPassword,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt,
        final boolean petInitiatedLetterEnabled
    ) {
        return new User(
            null,
            email,
            password,
            phoneNumber,
            role,
            status,
            provider,
            providerId,
            lastLoggedIn,
            lastChangedPassword,
            createdAt,
            updatedAt,
            petInitiatedLetterEnabled
        );
    }

    public boolean hasDifferentStatus(final UserStatus status) {
        return this.status != status;
    }

    public void login(final LocalDateTime loginTime) {
        this.lastLoggedIn = loginTime;
    }

    public void login(
        final OAuthProvider provider,
        final String providerId,
        final LocalDateTime loginTime
    ) {
        this.provider = provider;
        this.providerId = providerId;
        login(loginTime);
    }

    public void findPassword() {
        registerEvent(new FindUserPasswordMailEvent(this));
    }

    public void updatePassword(final String password, final LocalDateTime lastChangedPassword) {
        this.password = password;
        this.lastChangedPassword = lastChangedPassword;
    }

    public void updatePhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void leave() {
        this.status = UserStatus.LEAVE;
    }

    public void delete() {
        registerEvent(new DeleteUserEvent(this));
    }

    public enum UserRole {
        ROLE_USER, ROLE_ADMIN,
    }

    public enum UserStatus {
        INACTIVE,
        ACTIVE,
        SLEEP,
        LOCK,
        LEAVE,
    }

    public record UserId(Long value) {

        @Override
        public String toString() {
            return value.toString();
        }

    }
}
