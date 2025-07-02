package com.rainbowletter.server.medium;

import static com.rainbowletter.server.medium.RestDocsUtils.setSpecification;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rainbowletter.server.common.util.JwtTokenProvider;
import com.rainbowletter.server.user.application.domain.model.User.UserRole;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
public class TestHelper {

    protected static final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    protected String userAccessToken;
    protected String adminAccessToken;

    @LocalServerPort
    private int port;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUp(final RestDocumentationContextProvider provider) {
        RestAssured.port = port;
        final RequestSpecification specification = new RequestSpecBuilder()
            .addFilter(
                documentationConfiguration(provider)
                    .operationPreprocessors()
                    .withRequestDefaults(RestDocsUtils.removeHeaders())
                    .withResponseDefaults(RestDocsUtils.removeHeaders())
            )
            .addFilter(RestDocsUtils.getFilter())
            .build();
        setSpecification(specification);
        setUserAccessToken();
        setAdminAccessToken();
    }

    @AfterEach
    void clear() {
        databaseCleaner.execute();
    }

    void setUserAccessToken() {
        this.userAccessToken = jwtTokenProvider.create("user@mail.com", UserRole.ROLE_USER.name());
    }

    void setAdminAccessToken() {
        this.adminAccessToken = jwtTokenProvider.create("admin@mail.com",
            UserRole.ROLE_ADMIN.name());
    }

}
