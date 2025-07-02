package com.rainbowletter.server.user.adapter.out.infrastructure;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.common.util.TimeHolder;
import com.rainbowletter.server.user.application.domain.model.User;
import com.rainbowletter.server.user.application.port.out.LoadUserPort;
import com.rainbowletter.server.user.application.port.out.UpdateUserStatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class NativeUserDetailsService implements UserDetailsService {

    private final TimeHolder timeHolder;
    private final LoadUserPort loadUserPort;
    private final UpdateUserStatePort updateUserStatePort;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        try {
            final User user = loadUserPort.loadUserByEmail(username);
            user.login(timeHolder.currentTime());
            updateUserStatePort.updateUser(user);
            return new NativeUserDetails(user);
        } catch (final RainbowLetterException exception) {
            throw new UsernameNotFoundException(exception.getMessage());
        }
    }

}
