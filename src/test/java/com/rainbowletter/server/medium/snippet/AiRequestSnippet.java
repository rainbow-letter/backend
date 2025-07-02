package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class AiRequestSnippet {

    public static final Snippet AI_PROMPT_PATH_VARIABLE_ID = pathParameters(
        parameterWithName("id").description("프롬프트 ID")
    );

    public static final Snippet AI_OPTION_PATH_VARIABLE_ID = pathParameters(
        parameterWithName("id").description("옵션 ID")
    );

    public static final Snippet AI_CONFIG_UPDATE_REQUEST = requestFields(
        fieldWithPath("useABTest")
            .type(JsonFieldType.BOOLEAN)
            .description("A/B 테스트 사용 여부"),
        fieldWithPath("selectPrompt")
            .type(JsonFieldType.STRING)
            .description("사용할 프롬프트")
            .attributes(constraints("A || B"))
    );

    public static final Snippet AI_PROMPT_UPDATE_REQUEST = requestFields(
        fieldWithPath("provider")
            .type(JsonFieldType.STRING)
            .description("AI 제공자")
            .attributes(constraints("OPENAI || GEMINI")),
        fieldWithPath("model")
            .type(JsonFieldType.STRING)
            .description("AI 모델명"),
        fieldWithPath("system")
            .type(JsonFieldType.STRING)
            .description("system 프롬프트"),
        fieldWithPath("user")
            .type(JsonFieldType.STRING)
            .description("user 프롬프트"),
        fieldWithPath("parameters[]")
            .type(JsonFieldType.ARRAY)
            .description("user 프롬프트에 사용되는 파라미터 목록")
    );

    public static final Snippet AI_OPTION_UPDATE_REQUEST = requestFields(
        fieldWithPath("maxTokens")
            .type(JsonFieldType.NUMBER)
            .description("maxTokens"),
        fieldWithPath("temperature")
            .type(JsonFieldType.NUMBER)
            .description("temperature"),
        fieldWithPath("topP")
            .type(JsonFieldType.NUMBER)
            .description("topP"),
        fieldWithPath("frequencyPenalty")
            .type(JsonFieldType.NUMBER)
            .description("frequencyPenalty"),
        fieldWithPath("presencePenalty")
            .type(JsonFieldType.NUMBER)
            .description("presencePenalty"),
        fieldWithPath("stop[]")
            .type(JsonFieldType.ARRAY)
            .description("stop 목록")
    );

}
