package com.rainbowletter.server.common.config.security;

import com.rainbowletter.server.common.config.security.uri.AccessAllowUri;
import com.rainbowletter.server.common.config.security.uri.AdminAllowUri;
import com.rainbowletter.server.common.config.security.uri.AllowUri;
import com.rainbowletter.server.common.config.security.uri.AnonymousAllowUri;
import com.rainbowletter.server.user.adapter.out.infrastructure.OAuthUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class SecurityConfig implements WebMvcConfigurer {

    private final OAuthUserDetailsService oAuthUserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

    private static final String[] PERMIT_PATHS = {
        "/swagger-ui/**", "/v3/api-docs/**", "/api/shared-letters/sample"
    };

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 엔드포인트에 대해 적용
                .allowedOrigins("*") // 모든 도메인 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .exposedHeaders("*") // 응답 헤더에서 노출할 헤더 추가
                .allowCredentials(false) // 인증 정보 포함 여부
                .maxAge(3600); // 캐싱 시간 (초 단위)
    }


    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        configureBasicSecurity(http);
        configureAuthorizationSecurity(http);
        configureSecurityExceptionHandler(http);
        configureSecurityFilter(http);
        configurationOAuth2(http);
        return http.build();
    }

    private void configureBasicSecurity(final HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void configureAuthorizationSecurity(final HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                        .permitAll()
                        .requestMatchers(convertUriToPathMatcher(AccessAllowUri.values())).permitAll()
                        .requestMatchers(convertUriToPathMatcher(AnonymousAllowUri.values())).anonymous()
                        .requestMatchers(convertUriToPathMatcher(AdminAllowUri.values())).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/letters/**").permitAll()
                    .requestMatchers(PERMIT_PATHS).permitAll()
                        .anyRequest().authenticated()
        );
    }

    private AntPathRequestMatcher[] convertUriToPathMatcher(final AllowUri[] allowUris) {
        return Arrays.stream(allowUris)
                .map(AllowUri::getUri)
                .map(AntPathRequestMatcher::antMatcher)
                .toArray(AntPathRequestMatcher[]::new);
    }

    private void configureSecurityExceptionHandler(final HttpSecurity http) throws Exception {
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler()));
    }

    private void configureSecurityFilter(final HttpSecurity http) {
        http.addFilterBefore(
                jwtTokenAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );
    }

    private void configurationOAuth2(final HttpSecurity http) throws Exception {
        http.oauth2Login(oauth -> oauth.authorizationEndpoint(
                        endpoint -> endpoint.baseUri("/api/oauth2/authorization/**"))
                .redirectionEndpoint(endpoint -> endpoint.baseUri("/api/login/oauth2/code/**"))
                .userInfoEndpoint(userInfo -> userInfo.userService(oAuthUserService))
                .successHandler(oAuthSuccessHandler));
    }

}
