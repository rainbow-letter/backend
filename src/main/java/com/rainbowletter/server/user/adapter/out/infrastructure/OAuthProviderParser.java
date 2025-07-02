package com.rainbowletter.server.user.adapter.out.infrastructure;

import com.rainbowletter.server.user.application.domain.model.OAuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;

interface OAuthProviderParser {

    boolean support(OAuthProvider provider);

    String getUsername(OAuth2User user);

    String getProviderId(OAuth2User user);

}
