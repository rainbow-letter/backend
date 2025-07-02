package com.rainbowletter.server.medium.e2e;

import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_KEY;
import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_TYPE;
import static com.rainbowletter.server.medium.RestDocsUtils.getFilter;
import static com.rainbowletter.server.medium.RestDocsUtils.getSpecification;
import static com.rainbowletter.server.medium.snippet.AiRequestSnippet.AI_CONFIG_UPDATE_REQUEST;
import static com.rainbowletter.server.medium.snippet.AiRequestSnippet.AI_OPTION_PATH_VARIABLE_ID;
import static com.rainbowletter.server.medium.snippet.AiRequestSnippet.AI_OPTION_UPDATE_REQUEST;
import static com.rainbowletter.server.medium.snippet.AiRequestSnippet.AI_PROMPT_PATH_VARIABLE_ID;
import static com.rainbowletter.server.medium.snippet.AiRequestSnippet.AI_PROMPT_UPDATE_REQUEST;
import static com.rainbowletter.server.medium.snippet.AiResponseSnippet.ADMIN_AI_PARAMETER_RESPONSES;
import static com.rainbowletter.server.medium.snippet.AiResponseSnippet.ADMIN_AI_RESPONSE;
import static com.rainbowletter.server.medium.snippet.CommonRequestSnippet.ADMIN_AUTHORIZATION_HEADER;
import static org.assertj.core.api.Assertions.assertThat;

import com.rainbowletter.server.ai.adapter.in.web.dto.UpdateAiConfigRequest;
import com.rainbowletter.server.ai.adapter.in.web.dto.UpdateAiOptionRequest;
import com.rainbowletter.server.ai.adapter.in.web.dto.UpdateAiPromptRequest;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.AiProvider;
import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import com.rainbowletter.server.ai.application.domain.model.Parameter;
import com.rainbowletter.server.ai.application.port.in.dto.AiPromptParameterResponse;
import com.rainbowletter.server.ai.application.port.in.dto.AiSettingResponse;
import com.rainbowletter.server.medium.TestHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql", "classpath:sql/ai.sql"})
class AiE2ETest extends TestHelper {

    @Test
    void Should_AiSettingResponse_When_Admin() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = getSetting(token);
        final AiSettingResponse result = response.body().as(AiSettingResponse.class);

        // then
        assertThat(result.config().id()).isEqualTo(1L);
        assertThat(result.prompts()).hasSize(2);
    }

    private ExtractableResponse<Response> getSetting(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, ADMIN_AI_RESPONSE))
            .when().get("/api/admins/ai/setting")
            .then().log().all().extract();
    }

    @Test
    void Should_AiPromptParameterResponses_When_Admin() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = getParameters(token);
        final AiPromptParameterResponse result = response.body()
            .as(AiPromptParameterResponse.class);

        // then
        assertThat(result.parameters()).hasSize(6);
    }

    private ExtractableResponse<Response> getParameters(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, ADMIN_AI_PARAMETER_RESPONSES))
            .when().get("/api/admins/ai/parameters")
            .then().log().all().extract();
    }

    @Test
    void Should_UpdateConfig_When_ValidRequest() {
        // given
        final String token = adminAccessToken;
        final UpdateAiConfigRequest request = new UpdateAiConfigRequest(true, PromptType.B);

        // when
        final ExtractableResponse<Response> response = updateConfig(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> updateConfig(
        final String token,
        final UpdateAiConfigRequest request
    ) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, AI_CONFIG_UPDATE_REQUEST))
            .when().put("/api/admins/ai/config")
            .then().log().all().extract();
    }

    @Test
    void Should_UpdatePrompt_When_ValidRequest() {
        // given
        final String token = adminAccessToken;
        final UpdateAiPromptRequest request = new UpdateAiPromptRequest(
            AiProvider.GEMINI,
            "model",
            "system",
            "사용자의 편지 수 : %s",
            List.of(Parameter.LETTER_COUNT)
        );

        // when
        final ExtractableResponse<Response> response = updatePrompt(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> updatePrompt(
        final String token,
        final UpdateAiPromptRequest request
    ) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(
                ADMIN_AUTHORIZATION_HEADER,
                AI_PROMPT_PATH_VARIABLE_ID,
                AI_PROMPT_UPDATE_REQUEST
            ))
            .when().put("/api/admins/ai/prompts/{id}", 1)
            .then().log().all().extract();
    }

    @Test
    void Should_UpdateOption_When_ValidRequest() {
        // given
        final String token = adminAccessToken;
        final UpdateAiOptionRequest request = new UpdateAiOptionRequest(
            1000,
            1.0,
            1.0,
            1.0,
            1.0,
            List.of("stop", "test")
        );

        // when
        final ExtractableResponse<Response> response = updateOption(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> updateOption(
        final String token,
        final UpdateAiOptionRequest request
    ) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(
                ADMIN_AUTHORIZATION_HEADER,
                AI_OPTION_PATH_VARIABLE_ID,
                AI_OPTION_UPDATE_REQUEST
            ))
            .when().put("/api/admins/ai/options/{id}", 1)
            .then().log().all().extract();
    }

}
