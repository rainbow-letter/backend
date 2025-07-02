package com.rainbowletter.server.medium.e2e;

import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_KEY;
import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_TYPE;
import static com.rainbowletter.server.medium.RestDocsUtils.getFilter;
import static com.rainbowletter.server.medium.RestDocsUtils.getSpecification;
import static com.rainbowletter.server.medium.snippet.CommonRequestSnippet.ADMIN_AUTHORIZATION_HEADER;
import static com.rainbowletter.server.medium.snippet.FaqRequestSnippet.FAQ_CREATE_REQUEST;
import static com.rainbowletter.server.medium.snippet.FaqRequestSnippet.FAQ_PATH_VARIABLE_ID;
import static com.rainbowletter.server.medium.snippet.FaqRequestSnippet.FAQ_SWITCH_SEQUENCE_REQUEST;
import static com.rainbowletter.server.medium.snippet.FaqRequestSnippet.FAQ_UPDATE_REQUEST;
import static com.rainbowletter.server.medium.snippet.FaqResponseSnippet.ADMIN_FAQ_RESPONSES;
import static com.rainbowletter.server.medium.snippet.FaqResponseSnippet.FAQ_CREATE_RESPONSE_HEADER;
import static com.rainbowletter.server.medium.snippet.FaqResponseSnippet.VISIBILITY_FAQ_RESPONSES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.rainbowletter.server.faq.adapter.in.web.dto.RegisterFaqRequest;
import com.rainbowletter.server.faq.adapter.in.web.dto.SwitchFaqSequenceRequest;
import com.rainbowletter.server.faq.adapter.in.web.dto.UpdateFaqContentRequest;
import com.rainbowletter.server.faq.application.port.in.dto.FaqsForAdminResponse;
import com.rainbowletter.server.faq.application.port.in.dto.FaqsForUserResponse;
import com.rainbowletter.server.medium.TestHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql", "classpath:sql/faq.sql"})
class FaqE2ETest extends TestHelper {

    @Test
    void Should_FaqUserResponses_When_UnAuthenticated() {
        // given
        // when
        final ExtractableResponse<Response> response = findAllByVisibility();
        final FaqsForUserResponse result = response.body().as(FaqsForUserResponse.class);

        // then
        assertThat(result.faqs()).hasSize(2);
        assertThat(result.faqs())
            .extracting("id", "summary", "detail")
            .contains(
                tuple(1L, "무지개 편지는 무슨 서비스인가요?", "무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다."),
                tuple(2L, "답장이 온 건 어떻게 알 수 있나요?", "답장이 도착하면 등록하신 이메일 주소로 메일을 보내드려요.")
            );
    }

    private ExtractableResponse<Response> findAllByVisibility() {
        return RestAssured
            .given(getSpecification()).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(VISIBILITY_FAQ_RESPONSES))
            .when().get("/api/faqs")
            .then().log().all().extract();
    }

    @Test
    void Should_FaqAdminResponses_When_Admin() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = findAll(token);
        final FaqsForAdminResponse result = response.body().as(FaqsForAdminResponse.class);

        // then
        assertThat(result.faqs()).hasSize(2);
        assertThat(result.faqs())
            .extracting("id", "summary", "detail", "visibility", "sequence")
            .contains(
                tuple(1L, "무지개 편지는 무슨 서비스인가요?", "무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.", true,
                    1L),
                tuple(2L, "답장이 온 건 어떻게 알 수 있나요?", "답장이 도착하면 등록하신 이메일 주소로 메일을 보내드려요.", true, 2L)
            );
    }

    private ExtractableResponse<Response> findAll(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, ADMIN_FAQ_RESPONSES))
            .when().get("/api/admins/faqs")
            .then().log().all().extract();
    }

    @Test
    void Should_CreateFaq_When_Admin() {
        // given
        final String token = adminAccessToken;
        final RegisterFaqRequest request = new RegisterFaqRequest("FAQ 제목", "FAQ 본문");

        // when
        final ExtractableResponse<Response> response = create(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> create(
        final String token,
        final RegisterFaqRequest request
    ) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter()
                .document(ADMIN_AUTHORIZATION_HEADER, FAQ_CREATE_REQUEST,
                    FAQ_CREATE_RESPONSE_HEADER))
            .when().post("/api/admins/faqs")
            .then().log().all().extract();
    }

    @Test
    void Should_UpdateFaq_When_Admin() {
        // given
        final String token = adminAccessToken;
        final var request = new UpdateFaqContentRequest("FAQ 수정 제목", "FAQ 수정 본문");

        // when
        final ExtractableResponse<Response> response = update(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> update(
        final String token,
        final UpdateFaqContentRequest request
    ) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, FAQ_PATH_VARIABLE_ID,
                FAQ_UPDATE_REQUEST))
            .when().put("/api/admins/faqs/{id}", 1L)
            .then().log().all().extract();
    }

    @Test
    void Should_ChangeVisibilityFaq_When_Admin() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = changeVisibility(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> changeVisibility(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, FAQ_PATH_VARIABLE_ID))
            .when().put("/api/admins/faqs/visibility/{id}", 1L)
            .then().log().all().extract();
    }

    @Test
    void Should_SwitchSequenceFaq_When_Admin() {
        // given
        final String token = adminAccessToken;
        final SwitchFaqSequenceRequest request = new SwitchFaqSequenceRequest(1L, 2L);

        // when
        final ExtractableResponse<Response> response = switchSequence(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> switchSequence(
        final String token,
        final SwitchFaqSequenceRequest request
    ) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, FAQ_SWITCH_SEQUENCE_REQUEST))
            .when().put("/api/admins/faqs/switch-sequence")
            .then().log().all().extract();
    }

    @Test
    void Should_DeleteFaq_When_Admin() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = delete(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> delete(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, FAQ_PATH_VARIABLE_ID))
            .when().delete("/api/admins/faqs/{id}", 1L)
            .then().log().all().extract();
    }

}
