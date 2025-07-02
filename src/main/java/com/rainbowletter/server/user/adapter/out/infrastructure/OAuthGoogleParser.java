package com.rainbowletter.server.user.adapter.out.infrastructure;

import com.rainbowletter.server.user.application.domain.model.OAuthProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
class OAuthGoogleParser implements OAuthProviderParser {

    @Override
    public boolean support(final OAuthProvider provider) {
        return OAuthProvider.GOOGLE.equals(provider);
    }

    @Override
    public String getUsername(final OAuth2User user) {
        return user.getAttributes().get("email").toString();
    }

    @Override
    public String getProviderId(final OAuth2User user) {
        return user.getAttributes().get("sub").toString();
    }

}
