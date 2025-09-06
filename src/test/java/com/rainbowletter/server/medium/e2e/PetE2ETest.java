package com.rainbowletter.server.medium.e2e;

import com.rainbowletter.server.medium.TestHelper;
import com.rainbowletter.server.pet.adapter.in.web.dto.CreatePetRequest;
import com.rainbowletter.server.pet.adapter.in.web.dto.UpdatePetRequest;
import com.rainbowletter.server.pet.application.port.in.dto.PetDashboardResponses;
import com.rainbowletter.server.pet.application.port.in.dto.PetResponse;
import com.rainbowletter.server.pet.application.port.in.dto.PetsResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_KEY;
import static com.rainbowletter.server.common.util.Constants.AUTHORIZATION_HEADER_TYPE;
import static com.rainbowletter.server.medium.RestDocsUtils.getFilter;
import static com.rainbowletter.server.medium.RestDocsUtils.getSpecification;
import static com.rainbowletter.server.medium.snippet.CommonRequestSnippet.AUTHORIZATION_HEADER;
import static com.rainbowletter.server.medium.snippet.PetRequestSnippet.*;
import static com.rainbowletter.server.medium.snippet.PetResponseSnippet.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@Sql({"classpath:sql/user.sql", "classpath:sql/pet.sql"})
class PetE2ETest extends TestHelper {

    @Test
    void Should_PetResponses_When_Authenticated() {
        // given
        final String token = userAccessToken;
        final var deathAnniversary = LocalDate.of(2023, 1, 1);

        // when
        final ExtractableResponse<Response> response = findAllByEmail(token);
        final PetsResponse result = response.body().as(PetsResponse.class);

        // then
        assertThat(result.pets()).hasSize(2);
        assertThat(result.pets())
            .extracting("id", "userId", "name", "species", "owner", "personalities",
                "deathAnniversary", "image")
            .contains(
                tuple(1L, 1L, "콩이", "고양이", "형아", List.of("활발한", "잘삐짐"), deathAnniversary,
                    "objectKey"),
                tuple(2L, 1L, "미키", "강아지", "엄마", List.of(), deathAnniversary, null)
            );
    }

    private ExtractableResponse<Response> findAllByEmail(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(AUTHORIZATION_HEADER, PET_RESPONSES))
            .when().get("/api/pets")
            .then().log().all().extract();
    }

    @Test
    void Should_PetResponse_When_Authenticated() {
        // given
        final String token = userAccessToken;
        final var deathAnniversary = LocalDate.of(2023, 1, 1);

        // when
        final ExtractableResponse<Response> response = findByIdAndEmail(token);
        final PetResponse result = response.body().as(PetResponse.class);

        // then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.userId()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("콩이");
        assertThat(result.species()).isEqualTo("고양이");
        assertThat(result.owner()).isEqualTo("형아");
        assertThat(result.personalities()).contains("활발한", "잘삐짐");
        assertThat(result.deathAnniversary()).isEqualTo(deathAnniversary);
        assertThat(result.image()).isEqualTo("objectKey");
    }

    private ExtractableResponse<Response> findByIdAndEmail(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(AUTHORIZATION_HEADER, PET_PATH_VARIABLE_ID, PET_RESPONSE))
            .when().get("/api/pets/{id}", 1L)
            .then().log().all().extract();
    }

    @Test
    void Should_PetDashboardResponses_When_Authenticated() {
        // given
        final String token = userAccessToken;

        // when
        final ExtractableResponse<Response> response = findDashboard(token);
        final PetDashboardResponses result = response.body().as(PetDashboardResponses.class);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
        assertThat(result.pets()).hasSize(2);
    }

    private ExtractableResponse<Response> findDashboard(final String token) {
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .filter(getFilter().document(AUTHORIZATION_HEADER, PET_DASHBOARD_RESPONSES))
            .when().get("/api/pets/dashboard")
            .then().log().all().extract();
    }

    @Test
    void Should_CreatePet_When_ValidRequest() throws IOException {
        // given
        final String token = userAccessToken;

        final HashSet<String> personalities = new HashSet<>(List.of("용맹한", "잘삐짐"));
        final var deathAnniversary = LocalDate.of(2024, 1, 1);
        final var request = new CreatePetRequest("두부", "고양이", "형아", personalities, deathAnniversary,
            "objectKey");

        // when
        final ExtractableResponse<Response> response = create(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(201);
    }

    private ExtractableResponse<Response> create(
        final String token,
        final CreatePetRequest request
    ) throws IOException {
        final String requestBody = objectMapper.writeValueAsString(request);
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(requestBody)
            .filter(getFilter().document(AUTHORIZATION_HEADER, PET_CREATE_REQUEST,
                PET_CREATE_RESPONSE_HEADER))
            .when().post("/api/pets")
            .then().log().all().extract();
    }

    @Test
    void Should_UpdatePet_When_ValidRequest() throws IOException {
        // given
        final String token = userAccessToken;

        final HashSet<String> personalities = new HashSet<>(List.of("용맹한", "잘삐짐"));
        final var deathAnniversary = LocalDate.of(2024, 1, 1);
        final var request = new UpdatePetRequest("루이", "토끼", "형님", personalities, deathAnniversary,
            "objectKey");

        // when
        final ExtractableResponse<Response> response = update(token, request);

        // then
        assertThat(response.statusCode()).isEqualTo(200);
    }

    private ExtractableResponse<Response> update(
        final String token,
        final UpdatePetRequest request
    ) throws IOException {
        final String requestBody = objectMapper.writeValueAsString(request);
        return RestAssured
            .given(getSpecification()).log().all()
            .header(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_HEADER_TYPE + " " + token)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(requestBody)
            .filter(getFilter().document(AUTHORIZATION_HEADER, PET_PATH_VARIABLE_ID,
                PET_UPDATE_REQUEST))
            .when().put("/api/pets/{id}", 1)
            .then().log().all().extract();
    }

    @Test
    void Should_DeletePet_When_ValidRequest() {
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
            .filter(getFilter().document(AUTHORIZATION_HEADER, PET_PATH_VARIABLE_ID))
            .when().delete("/api/pets/{id}", 2)
            .then().log().all().extract();
    }

}
