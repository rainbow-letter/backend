package com.rainbowletter.server.medium.e2e;

import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_KEY;
import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_TYPE;
import static com.rainbowletter.server.medium.RestDocsUtils.getFilter;
import static com.rainbowletter.server.medium.RestDocsUtils.getSpecification;
import static com.rainbowletter.server.medium.snippet.CommonRequestSnippet.ADMIN_AUTHORIZATION_HEADER;
import static com.rainbowletter.server.medium.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.rainbowletter.server.medium.snippet.ReplyRequestSnippet.REPLY_PATH_VARIABLE_ID;
import static com.rainbowletter.server.medium.snippet.ReplyRequestSnippet.REPLY_PATH_VARIABLE_LETTER_ID;
import static com.rainbowletter.server.medium.snippet.ReplyRequestSnippet.REPLY_UPDATE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import com.rainbowletter.server.ai.application.domain.model.AiPrompt.PromptType;
import com.rainbowletter.server.letter.application.domain.model.Letter.LetterId;
import com.rainbowletter.server.medium.TestHelper;
import com.rainbowletter.server.pet.application.domain.model.Pet.PetId;
import com.rainbowletter.server.reply.adapter.in.web.dto.ReplyUpdateRequest;
import com.rainbowletter.server.reply.application.domain.model.Reply;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyId;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyReadStatus;
import com.rainbowletter.server.reply.application.domain.model.Reply.ReplyStatus;
import com.rainbowletter.server.reply.application.port.out.GenerateReplyCommand;
import com.rainbowletter.server.reply.application.port.out.GenerateReplyPort;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

@Sql({"classpath:sql/user.sql", "classpath:sql/pet.sql", "classpath:sql/letter.sql",
    "classpath:sql/reply.sql"})
class ReplyE2ETest extends TestHelper {

    @MockBean
    private GenerateReplyPort generateReplyPort;

    @Test
    void Should_GenerateReply_When_Admin() {
        // given
        final String token = adminAccessToken;
        BDDMockito.given(generateReplyPort.generate(any(GenerateReplyCommand.class)))
            .willReturn(Reply.withId(
                new ReplyId(1L),
                new PetId(1L),
                new LetterId(1L),
                "제목",
                "본문",
                "프롬프트 A",
                "프롬프트 B",
                PromptType.A,
                false,
                LocalDateTime.now(),
                ReplyStatus.CHAT_GPT,
                LocalDateTime.now(),
                ReplyReadStatus.UNREAD,
                LocalDateTime.now(),
                LocalDateTime.now()
            ));

        // when
        final ExtractableResponse<Response> response = generate(token);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> generate(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, REPLY_PATH_VARIABLE_LETTER_ID))
            .when().post("/api/admins/replies/generate/{letterId}", 1)
            .then().log().all().extract();
    }

    @Test
    void Should_InspectReply_When_Admin() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = inspect(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> inspect(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, REPLY_PATH_VARIABLE_ID))
            .when().post("/api/admins/replies/inspect/{id}", 2)
            .then().log().all().extract();
    }

    @Test
    void Should_SubmitReply_When_Admin() {
        // given
        final String token = adminAccessToken;

        // when
        final ExtractableResponse<Response> response = submit(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> submit(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, REPLY_PATH_VARIABLE_ID))
            .when().post("/api/admins/replies/submit/{id}", 4)
            .then().log().all().extract();
    }

    @Test
    void Should_UpdateReply_When_Admin() {
        // given
        final String token = adminAccessToken;
        final ReplyUpdateRequest request = new ReplyUpdateRequest(PromptType.A, "수정 제목", "수정 본문");

        // when
        final ExtractableResponse<Response> response = update(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> update(final String token,
        final ReplyUpdateRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(ADMIN_AUTHORIZATION_HEADER, REPLY_PATH_VARIABLE_ID,
                REPLY_UPDATE_REQUEST))
            .when().put("/api/admins/replies/{id}", 1)
            .then().log().all().extract();
    }

    @Test
    void Should_ReadReply_When_Admin() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = read(token);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> read(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(AUTHORIZATION_HEADER, REPLY_PATH_VARIABLE_ID))
            .when().post("/api/replies/read/{id}", 3)
            .then().log().all().extract();
    }

}
