package com.rainbowletter.server.user.adapter.out.infrastructure;

import com.rainbowletter.server.common.annotation.InfraAdapter;
import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.common.util.TimeHolder;
import com.rainbowletter.server.common.util.UuidHolder;
import com.rainbowletter.server.user.application.domain.model.OAuthProvider;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.domain.model.User.UserRole;
import com.rainbowletter.server.user.application.domain.model.User.UserStatus;
import com.rainbowletter.server.user.application.port.out.ExistsUserPort;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import com.rainbowletter.server.user.application.port.out.RegisterUserPort;
import com.rainbowletter.server.user.application.port.out.UpdateUserStatePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

@InfraAdapter
@RequiredArgsConstructor
class OAuthLoginHandlerImpl implements OAuthLoginHandler {

    private final LoadUserPort loadUserPort;
    private final ExistsUserPort existsUserPort;
    private final RegisterUserPort registerUserPort;
    private final UpdateUserStatePort updateUserStatePort;

    private final UuidHolder uuidHolder;
    private final TimeHolder timeHolder;
    private final PasswordEncoder passwordEncoder;
    private final List<OAuthProviderParser> parsers;

    @Override
    @Transactional
    public User login(final String registrationId, final OAuth2User user) {
        final OAuthProvider provider = OAuthProvider.get(registrationId);
        final OAuthProviderParser oAuthProviderParser = parsers.stream()
            .filter(parser -> parser.support(provider))
            .findAny()
            .orElseThrow(() -> new RainbowLetterException("현재 지원하지 않는 로그인 타입(%s) 입니다.", provider));
        final String email = oAuthProviderParser.getUsername(user);
        final String providerId = oAuthProviderParser.getProviderId(user);

        if (existsUserPort.existsUserByEmail(email)) {
            return updateUser(email, provider, providerId);
        }
        return registerUser(email, provider, providerId);
    }

    private User updateUser(
        final String email,
        final OAuthProvider provider,
        final String providerId
    ) {
        final User user = loadUserPort.loadUserByEmail(email);
        user.login(provider, providerId, timeHolder.currentTime());
        updateUserStatePort.updateUser(user);
        return user;
    }

    private User registerUser(
        final String email,
        final OAuthProvider provider,
        final String providerId
    ) {
        final User user = User.withoutId(
            email,
            passwordEncoder.encode(uuidHolder.generate().toString()),
            null,
            UserRole.ROLE_USER,
            UserStatus.ACTIVE,
            provider,
            providerId,
            timeHolder.currentTime(),
            timeHolder.currentTime(),
            timeHolder.currentTime(),
            timeHolder.currentTime()
        );
        return registerUserPort.registerUser(user);
    }

}
