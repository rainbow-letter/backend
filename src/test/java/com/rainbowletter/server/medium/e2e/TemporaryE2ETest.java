package com.rainbowletter.server.medium.e2e;

import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_KEY;
import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_TYPE;
import static com.rainbowletter.server.medium.RestDocsUtils.getFilter;
import static com.rainbowletter.server.medium.RestDocsUtils.getSpecification;
import static com.rainbowletter.server.medium.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.rainbowletter.server.medium.snippet.TemporaryRequestSnippet.TEMPORARY_PATH_VARIABLE_ID;
import static com.rainbowletter.server.medium.snippet.TemporaryRequestSnippet.TEMPORARY_PET_QUERY_PARAMS;
import static com.rainbowletter.server.medium.snippet.TemporaryRequestSnippet.TEMPORARY_UPDATE_REQUEST;
import static com.rainbowletter.server.medium.snippet.TemporaryResponseSnippet.TEMPORARY_CREATE_RESPONSE;
import static com.rainbowletter.server.medium.snippet.TemporaryResponseSnippet.TEMPORARY_EXISTS_RESPONSE;
import static com.rainbowletter.server.medium.snippet.TemporaryResponseSnippet.TEMPORARY_RESPONSE;
import static com.rainbowletter.server.medium.snippet.TemporaryResponseSnippet.TEMPORARY_SESSION_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;

import com.rainbowletter.server.medium.TestHelper;
import com.rainbowletter.server.temporary.adapter.in.web.dto.CreateTemporaryRequest;
import com.rainbowletter.server.temporary.adapter.in.web.dto.UpdateTemporaryRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql", "classpath:sql/pet.sql", "classpath:sql/temporary.sql"})
class TemporaryE2ETest extends TestHelper {

    @Test
    void Should_TemporaryExistsResponse_When_Exists() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = exists(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> exists(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParams("pet", 1L)
            .filter(getFilter().document(
                AUTHORIZATION_HEADER,
                TEMPORARY_PET_QUERY_PARAMS,
                TEMPORARY_EXISTS_RESPONSE
            ))
            .when().get("/api/temporaries/exists")
            .then().log().all().extract();
    }

    @Test
    void Should_TemporaryResponse_When_FindByPetId() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = findByPetId(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> findByPetId(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParams("pet", 1L)
            .filter(getFilter().document(
                AUTHORIZATION_HEADER,
                TEMPORARY_PET_QUERY_PARAMS,
                TEMPORARY_RESPONSE
            ))
            .when().get("/api/temporaries")
            .then().log().all().extract();
    }

    @Test
    void Should_Create_When_ValidRequest() {
        // given
        final String token = userAccessToken;
        final CreateTemporaryRequest request = new CreateTemporaryRequest(2L, "임시 저장 본문");

        // when
        final ExtractableResponse<Response> response = create(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> create(final String token,
        final CreateTemporaryRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(
                AUTHORIZATION_HEADER,
                TEMPORARY_UPDATE_REQUEST,
                TEMPORARY_CREATE_RESPONSE
            ))
            .when().post("/api/temporaries")
            .then().log().all().extract();
    }

    @Test
    void Should_Update_When_ValidRequest() {
        // given
        final String token = userAccessToken;
        final UpdateTemporaryRequest request = new UpdateTemporaryRequest(1L, "수정 본문");

        // when
        final ExtractableResponse<Response> response = update(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> update(final String token,
        final UpdateTemporaryRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(
                AUTHORIZATION_HEADER,
                TEMPORARY_PATH_VARIABLE_ID,
                TEMPORARY_UPDATE_REQUEST
            ))
            .when().put("/api/temporaries/{id}", 2)
            .then().log().all().extract();
    }

    @Test
    void Should_TemporarySessionResponse_When_ChangeSession() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = changeSession(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> changeSession(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(
                AUTHORIZATION_HEADER,
                TEMPORARY_PATH_VARIABLE_ID,
                TEMPORARY_SESSION_RESPONSE
            ))
            .when().put("/api/temporaries/session/{id}", 2)
            .then().log().all().extract();
    }

    @Test
    void Should_DeleteTemporary_When_ValidRequest() {
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
            .queryParams("pet", 1)
            .filter(getFilter().document(
                AUTHORIZATION_HEADER,
                TEMPORARY_PATH_VARIABLE_ID,
                TEMPORARY_PET_QUERY_PARAMS
            ))
            .when().delete("/api/temporaries/{id}", 2)
            .then().log().all().extract();
    }

}
