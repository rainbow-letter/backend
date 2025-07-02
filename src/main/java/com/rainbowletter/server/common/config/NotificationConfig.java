package com.rainbowletter.server.common.config;

import com.rainbowletter.server.common.application.domain.exception.RainbowLetterException;
import com.rainbowletter.server.notification.adapter.out.infrastructure.AligoAlimTalkClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Getter
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class NotificationConfig {

    private static final String ALIGO_KAKAO_BASE_URL = "https://kakaoapi.aligo.in";

    @Value("${aligo.access-key}")
    private String accessKey;

    @Value("${aligo.sender-key}")
    private String senderKey;

    @Value("${aligo.sender}")
    private String sender;

    @Bean
    AligoAlimTalkClient alimTalkClient() {
        final RestClient restClient = RestClient.builder()
            .baseUrl(ALIGO_KAKAO_BASE_URL)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .defaultStatusHandler(HttpStatusCode::is5xxServerError, ((request, response) -> {
                throw new RainbowLetterException("alimtalk.server.error");
            }))
            .build();
        return HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build()
            .createClient(AligoAlimTalkClient.class);
    }

}
