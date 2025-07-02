package com.rainbowletter.server.medium.e2e;

import static com.rainbowletter.server.medium.RestDocsUtils.getFilter;
import static com.rainbowletter.server.medium.RestDocsUtils.getSpecification;
import static com.rainbowletter.server.medium.snippet.DataRequestSnippet.DATA_CREATE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;

import com.rainbowletter.server.log.adapter.in.web.dto.LogUserAgentRequest;
import com.rainbowletter.server.medium.TestHelper;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class LogUserAgentE2ETest extends TestHelper {

    @Test
    void Should_Create_When_ValidRequest() {
        // given
        final LogUserAgentRequest request = new LogUserAgentRequest("이벤트 명");

        // when
        final ExtractableResponse<Response> response = create(request);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> create(final LogUserAgentRequest request) {
        return RestAssured
            .given(getSpecification()).log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .filter(getFilter().document(DATA_CREATE_REQUEST))
            .when().post("/api/data")
            .then().log().all().extract();
    }

}
