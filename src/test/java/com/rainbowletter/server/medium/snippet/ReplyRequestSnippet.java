package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class ReplyRequestSnippet {

    public static final Snippet REPLY_PATH_VARIABLE_LETTER_ID = pathParameters(
        parameterWithName("letterId").description("편지 ID")
    );

    public static final Snippet REPLY_PATH_VARIABLE_ID = pathParameters(
        parameterWithName("id").description("답장 ID")
    );

    public static final Snippet REPLY_UPDATE_REQUEST = requestFields(
        fieldWithPath("promptType").type(JsonFieldType.STRING)
            .description("최종 선택한 프롬프트 타입")
            .attributes(constraints("A 또는 B")),
        fieldWithPath("summary").type(JsonFieldType.STRING)
            .description("수정할 답장 제목")
            .attributes(constraints("20자 이내")),
        fieldWithPath("content").type(JsonFieldType.STRING)
            .description("수정할 답장 본문")
            .attributes(constraints("1000자 이내"))
    );

}
