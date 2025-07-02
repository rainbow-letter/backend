package com.rainbowletter.server.user.adapter.out.infrastructure;

import com.rainbowletter.server.user.application.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthUserDetailsService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuthLoginHandler oAuthLoginHandler;
    private final DefaultOAuth2UserService defaultOAuth2UserService;

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest)
        throws OAuth2AuthenticationException {

        final OAuth2User oAuthUser = defaultOAuth2UserService.loadUser(userRequest);
        final String registrationId = userRequest.getClientRegistration().getRegistrationId();
        final User user = oAuthLoginHandler.login(registrationId, oAuthUser);
        return new OAuthUserDetails(user, oAuthUser.getAttributes());
    }

}
