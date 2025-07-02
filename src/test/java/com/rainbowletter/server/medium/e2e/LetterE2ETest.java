package com.rainbowletter.server.medium.e2e;

import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_KEY;
import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_TYPE;
import static com.rainbowletter.server.medium.RestDocsUtils.getFilter;
import static com.rainbowletter.server.medium.RestDocsUtils.getSpecification;
import static com.rainbowletter.server.medium.snippet.CommonRequestSnippet.ADMIN_AUTHORIZATION_HEADER;
import static com.rainbowletter.server.medium.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.rainbowletter.server.medium.snippet.LetterRequestSnippet.LETTER_ADMIN_DETAIL_QUERY_PARAMS;
import static com.rainbowletter.server.medium.snippet.LetterRequestSnippet.LETTER_ADMIN_QUERY_PARAMS;
import static com.rainbowletter.server.medium.snippet.LetterRequestSnippet.LETTER_BOX_QUERY_PARAMS;
import static com.rainbowletter.server.medium.snippet.LetterRequestSnippet.LETTER_CREATE_REQUEST;
import static com.rainbowletter.server.medium.snippet.LetterRequestSnippet.LETTER_PATH_VARIABLE_ID;
import static com.rainbowletter.server.medium.snippet.LetterRequestSnippet.LETTER_PATH_VARIABLE_SHARE_LINK;
import static com.rainbowletter.server.medium.snippet.LetterRequestSnippet.LETTER_QUERY_PARAM_PET_ID;
import static com.rainbowletter.server.medium.snippet.LetterResponseSnippet.LETTER_ADMIN_DETAIL_RESPONSE;
import static com.rainbowletter.server.medium.snippet.LetterResponseSnippet.LETTER_ADMIN_PAGE_RESPONSE;
import static com.rainbowletter.server.medium.snippet.LetterResponseSnippet.LETTER_BOX_RESPONSE;
import static com.rainbowletter.server.medium.snippet.LetterResponseSnippet.LETTER_CREATE_RESPONSE_HEADER;
import static com.rainbowletter.server.medium.snippet.LetterResponseSnippet.LETTER_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;

import com.rainbowletter.server.letter.adapter.in.web.dto.CreateLetterRequest;
import com.rainbowletter.server.letter.adapter.in.web.dto.LetterDetailResponse;
import com.rainbowletter.server.medium.TestHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql", "classpath:sql/pet.sql", "classpath:sql/letter.sql",
    "classpath:sql/reply.sql"})
class LetterE2ETest extends TestHelper {

    @Test
    void Should_LetterBoxResponses_When_ValidRequest() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = findAllLetterBoxByEmail(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> findAllLetterBoxByEmail(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParams("pet", 1, "start", "2024-01-01", "end", "2024-01-02")
            .filter(getFilter().document(AUTHORIZATION_HEADER, LETTER_BOX_QUERY_PARAMS,
                LETTER_BOX_RESPONSE))
            .when().get("/api/letters/box")
            .then().log().all().extract();
    }

    @Test
    void Should_LetterResponse_When_ValidRequest() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = findById(token);
        final LetterDetailResponse result = response.body().as(LetterDetailResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.pet().id()).isEqualTo(1);
        assertThat(result.letter().id()).isEqualTo(1);
        assertThat(result.reply().id()).isEqualTo(1);
    }

    private ExtractableResponse<Response> findById(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(AUTHORIZATION_HEADER, LETTER_PATH_VARIABLE_ID,
                LETTER_RESPONSE))
            .when().get("/api/letters/{id}", 1)
            .then().log().all().extract();
    }

    @Test
    void Should_LetterShareResponse_When_ValidRequest() {
        // given
        // when
        final ExtractableResponse<Response> response = findByShareLink();
        final LetterDetailResponse result = response.body().as(LetterDetailResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.pet().id()).isEqualTo(1);
        assertThat(result.letter().id()).isEqualTo(1);
        assertThat(result.reply().id()).isEqualTo(1);
    }

    private ExtractableResponse<Response> findByShareLink() {
        return RestAssured
            .given(getSpecification()).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(LETTER_PATH_VARIABLE_SHARE_LINK, LETTER_RESPONSE))
            .when().get("/api/letters/share/{shareLink}", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
            .then().log().all().extract();
    }

    @Test
    void Should_CreateLetter_When_ValidRequest() {
        // given
        final String token = userAccessToken;
        final var request = new CreateLetterRequest("편지 제목", "편지 내용",
            "202401/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        // when
        final ExtractableResponse<Response> response = create(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> create(final String token,
        final CreateLetterRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .queryParam("pet", 1)
            .filter(getFilter().document(
                AUTHORIZATION_HEADER,
                LETTER_QUERY_PARAM_PET_ID,
                LETTER_CREATE_REQUEST,
                LETTER_CREATE_RESPONSE_HEADER
            ))
            .when().post("/api/letters")
            .then().log().all().extract();
    }

    @Test
    void Should_DeleteLetter_When_ValidRequest() {
        // given
        final String token = userAccessToken;

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
            .filter(getFilter().document(AUTHORIZATION_HEADER, LETTER_PATH_VARIABLE_ID))
            .when().delete("/api/letters/{id}", 4)
            .then().log().all().extract();
    }

    @Test
    void Should_LetterAdminPageResponse_When_Admin() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = findAllByAdmin(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> findAllByAdmin(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParams(
                "start", "2024-01-01",
                "end", "2024-01-03",
                "email", "user@mail.com",
                "page", 0,
                "size", 3
            )
            .filter(getFilter().document(
                ADMIN_AUTHORIZATION_HEADER,
                LETTER_ADMIN_QUERY_PARAMS,
                LETTER_ADMIN_PAGE_RESPONSE
            ))
            .when().get("/api/admins/letters/list")
            .then().log().all().extract();
    }

    @Test
    void Should_LetterAdminDetailResponse_When_Admin() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = findByAdmin(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> findByAdmin(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("user", "1")
            .filter(getFilter().document(
                ADMIN_AUTHORIZATION_HEADER,
                LETTER_PATH_VARIABLE_ID,
                LETTER_ADMIN_DETAIL_QUERY_PARAMS,
                LETTER_ADMIN_DETAIL_RESPONSE
            ))
            .when().get("/api/admins/letters/{id}", 1)
            .then().log().all().extract();
    }

}
