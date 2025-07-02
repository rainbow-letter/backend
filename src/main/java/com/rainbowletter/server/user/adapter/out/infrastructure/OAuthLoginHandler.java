package com.rainbowletter.server.user.adapter.out.infrastructure;

import com.rainbowletter.server.user.application.domain.model.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

interface OAuthLoginHandler {

    User login(String registrationId, OAuth2User user);

}
