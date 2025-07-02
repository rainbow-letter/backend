package com.rainbowletter.server.medium.e2e;

import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_KEY;
import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_TYPE;
import static com.rainbowletter.server.medium.RestDocsUtils.getFilter;
import static com.rainbowletter.server.medium.RestDocsUtils.getSpecification;
import static com.rainbowletter.server.medium.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.rainbowletter.server.medium.snippet.UserRequestSnippet.USER_CHANGE_PASSWORD_REQUEST;
import static com.rainbowletter.server.medium.snippet.UserRequestSnippet.USER_CHANGE_PHONE_NUMBER_REQUEST;
import static com.rainbowletter.server.medium.snippet.UserRequestSnippet.USER_CREATE_REQUEST;
import static com.rainbowletter.server.medium.snippet.UserRequestSnippet.USER_FIND_PASSWORD_REQUEST;
import static com.rainbowletter.server.medium.snippet.UserRequestSnippet.USER_LOGIN_REQUEST;
import static com.rainbowletter.server.medium.snippet.UserRequestSnippet.USER_RESET_PASSWORD_REQUEST;
import static com.rainbowletter.server.medium.snippet.UserResponseSnippet.USER_CREATE_RESPONSE_HEADER;
import static com.rainbowletter.server.medium.snippet.UserResponseSnippet.USER_INFORMATION_RESPONSE;
import static com.rainbowletter.server.medium.snippet.UserResponseSnippet.USER_LOGIN_RESPONSE;
import static com.rainbowletter.server.medium.snippet.UserResponseSnippet.USER_VERIFY_RESPONSE;
import static org.assertj.core.api.Assertions.assertThat;

import com.rainbowletter.server.medium.TestHelper;
import com.rainbowletter.server.user.adapter.in.web.dto.ChangeUserPasswordRequest;
import com.rainbowletter.server.user.adapter.in.web.dto.ChangeUserPhoneNumberRequest;
import com.rainbowletter.server.user.adapter.in.web.dto.FindUserPasswordRequest;
import com.rainbowletter.server.user.adapter.in.web.dto.ResetUserPasswordRequest;
import com.rainbowletter.server.user.adapter.in.web.dto.UserLoginRequest;
import com.rainbowletter.server.user.adapter.in.web.dto.UserRegisterRequest;
import com.rainbowletter.server.user.application.domain.model.OAuthProvider;
import com.rainbowletter.server.user.application.domain.model.User.UserRole;
import com.rainbowletter.server.user.application.port.in.dto.TokenResponse;
import com.rainbowletter.server.user.application.port.in.dto.UserInformationResponse;
import com.rainbowletter.server.user.application.port.in.dto.UserVerifyResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql"})
class UserE2ETest extends TestHelper {

    @Test
    void Should_UserInformationResponse_When_Authenticated() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = information(token);
        final UserInformationResponse result = response.body().as(UserInformationResponse.class);

        // then
        assertThat(result.id()).isEqualTo(1);
        assertThat(result.email()).isEqualTo("user@mail.com");
        assertThat(result.phoneNumber()).isEqualTo("01012345678");
        assertThat(result.role()).isEqualTo(UserRole.ROLE_USER);
        assertThat(result.provider()).isEqualTo(OAuthProvider.NONE);
    }

    private ExtractableResponse<Response> information(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(AUTHORIZATION_HEADER, USER_INFORMATION_RESPONSE))
            .when().get("/api/users/info")
            .then().log().all().extract();
    }

    @Test
    void Should_UserVerifyResponse_When_Authenticated() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = verify(token);
        final UserVerifyResponse result = response.body().as(UserVerifyResponse.class);

        // then
        assertThat(result.email()).isEqualTo("user@mail.com");
        assertThat(result.role()).isEqualTo(UserRole.ROLE_USER.name());
    }

    private ExtractableResponse<Response> verify(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(AUTHORIZATION_HEADER, USER_VERIFY_RESPONSE))
            .when().get("/api/users/verify")
            .then().log().all().extract();
    }

    @Test
    void Should_CreateUser_When_ValidRequest() {
        // given
        final UserRegisterRequest request = new UserRegisterRequest("test@mail.com",
            "password1234");

        // when
        final ExtractableResponse<Response> response = create(request);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> create(final UserRegisterRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(USER_CREATE_REQUEST, USER_CREATE_RESPONSE_HEADER))
            .when().post("/api/users/create")
            .then().log().all().extract();
    }

    @Test
    void Should_LoginUser_When_ValidRequest() {
        // given
        final UserLoginRequest request = new UserLoginRequest("user@mail.com", "password1234");

        // when
        final ExtractableResponse<Response> response = login(request);
        final TokenResponse result = response.body().as(TokenResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.token()).isNotBlank();
    }

    private ExtractableResponse<Response> login(final UserLoginRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(USER_LOGIN_REQUEST, USER_LOGIN_RESPONSE))
            .when().post("/api/users/login")
            .then().log().all().extract();
    }

    @Test
    void Should_FindPasswordUser_When_ValidRequest() {
        // given
        final FindUserPasswordRequest request = new FindUserPasswordRequest("user@mail.com");

        // when
        final ExtractableResponse<Response> response = findPassword(request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> findPassword(final FindUserPasswordRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(USER_FIND_PASSWORD_REQUEST))
            .when().post("/api/users/find-password")
            .then().log().all().extract();
    }

    @Test
    void Should_ResetPasswordUser_When_ValidRequest() {
        // given
        final String token = userAccessToken;
        final ResetUserPasswordRequest request = new ResetUserPasswordRequest("!@#$password1234");

        // when
        final ExtractableResponse<Response> response = resetPassword(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> resetPassword(final String token,
        final ResetUserPasswordRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(AUTHORIZATION_HEADER, USER_RESET_PASSWORD_REQUEST))
            .when().put("/api/users/reset-password")
            .then().log().all().extract();
    }

    @Test
    void Should_ChangePasswordUser_When_ValidRequest() {
        // given
        final String token = userAccessToken;
        final var request = new ChangeUserPasswordRequest("password1234", "!@#$password1234");

        // when
        final ExtractableResponse<Response> response = changePassword(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> changePassword(final String token,
        final ChangeUserPasswordRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(AUTHORIZATION_HEADER, USER_CHANGE_PASSWORD_REQUEST))
            .when().put("/api/users/password")
            .then().log().all().extract();
    }

    @Test
    void Should_ChangeUserPhoneNumber_When_ValidRequest() {
        // given
        final String token = userAccessToken;
        final ChangeUserPhoneNumberRequest request = new ChangeUserPhoneNumberRequest(
            "01011112222");

        // when
        final ExtractableResponse<Response> response = changePhoneNumber(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> changePhoneNumber(
        final String token,
        final ChangeUserPhoneNumberRequest request
    ) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(AUTHORIZATION_HEADER, USER_CHANGE_PHONE_NUMBER_REQUEST))
            .when().put("/api/users/phone-number")
            .then().log().all().extract();
    }

    @Test
    void Should_DeleteUserPhoneNumber_When_Authenticated() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = deletePhoneNumber(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> deletePhoneNumber(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(AUTHORIZATION_HEADER))
            .when().delete("/api/users/phone-number")
            .then().log().all().extract();
    }

    @Test
    void Should_LeaveUser_When_Authenticated() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = leave(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> leave(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(AUTHORIZATION_HEADER))
            .when().delete("/api/users")
            .then().log().all().extract();
    }

}
