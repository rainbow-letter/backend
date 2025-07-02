package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class AiResponseSnippet {

    public static final Snippet ADMIN_AI_RESPONSE = responseFields(
        fieldWithPath("config.id")
            .type(JsonFieldType.NUMBER)
            .description("AI 설정 ID"),
        fieldWithPath("config.useABTest")
            .type(JsonFieldType.BOOLEAN)
            .description("A/B 테스트 사용 여부"),
        fieldWithPath("config.selectPrompt")
            .type(JsonFieldType.STRING)
            .description("메인으로 사용할 프롬프트"),
        fieldWithPath("prompts[]")
            .type(JsonFieldType.ARRAY)
            .description("프롬프트 목록"),
        fieldWithPath("prompts[].id")
            .type(JsonFieldType.NUMBER)
            .description("프롬프트 ID"),
        fieldWithPath("prompts[].configId")
            .type(JsonFieldType.NUMBER)
            .description("AI CONFIG ID"),
        fieldWithPath("prompts[].provider")
            .type(JsonFieldType.STRING)
            .description("AI 제공자")
            .attributes(constraints("OPENAI || GEMINI")),
        fieldWithPath("prompts[].type")
            .type(JsonFieldType.STRING)
            .description("프롬프트 타입")
            .attributes(constraints("A || B")),
        fieldWithPath("prompts[].model")
            .type(JsonFieldType.STRING)
            .description("AI 모델명"),
        fieldWithPath("prompts[].system")
            .type(JsonFieldType.STRING)
            .description("System 프롬프트"),
        fieldWithPath("prompts[].user")
            .type(JsonFieldType.STRING)
            .description("User 프롬프트"),
        fieldWithPath("prompts[].parameters[]")
            .type(JsonFieldType.ARRAY)
            .description("사용자 프롬프트 파라미터 목록")
            .attributes(constraints(
                "PET_NAME || PET_OWNER || PET_SPECIES || LETTER_CONTENT || LETTER_COUNT || FIRST_LETTER")),
        fieldWithPath("prompts[].option.id")
            .type(JsonFieldType.NUMBER)
            .description("프롬프트 옵션 ID"),
        fieldWithPath("prompts[].option.promptId")
            .type(JsonFieldType.NUMBER)
            .description("프롬프트 ID"),
        fieldWithPath("prompts[].option.maxTokens")
            .type(JsonFieldType.NUMBER)
            .description("maxTokens"),
        fieldWithPath("prompts[].option.temperature")
            .type(JsonFieldType.NUMBER)
            .description("temperature"),
        fieldWithPath("prompts[].option.topP")
            .type(JsonFieldType.NUMBER)
            .description("topP"),
        fieldWithPath("prompts[].option.frequencyPenalty")
            .type(JsonFieldType.NUMBER)
            .description("frequencyPenalty"),
        fieldWithPath("prompts[].option.presencePenalty")
            .type(JsonFieldType.NUMBER)
            .description("presencePenalty"),
        fieldWithPath("prompts[].option.stop[]")
            .type(JsonFieldType.ARRAY)
            .description("stop words STRING 목록")
    );

    public static final Snippet ADMIN_AI_PARAMETER_RESPONSES = responseFields(
        fieldWithPath("parameters[]")
            .type(JsonFieldType.ARRAY)
            .description("프롬프트 파라미터 목록")
            .attributes(constraints(
                "PET_NAME || PET_OWNER || PET_SPECIES || LETTER_CONTENT || LETTER_COUNT || FIRST_LETTER"))
    );

}
