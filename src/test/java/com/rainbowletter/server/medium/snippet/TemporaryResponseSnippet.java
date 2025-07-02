package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class TemporaryResponseSnippet {

	public static final Snippet TEMPORARY_EXISTS_RESPONSE = responseFields(
			fieldWithPath("exists")
					.type(JsonFieldType.BOOLEAN)
					.description("임시 저장 데이터 존재 여부")
	);

	public static final Snippet TEMPORARY_RESPONSE = responseFields(
			fieldWithPath("id")
					.type(JsonFieldType.NUMBER)
					.description("임시 저장 ID"),
			fieldWithPath("userId")
					.type(JsonFieldType.NUMBER)
					.description("사용자 ID"),
			fieldWithPath("petId")
					.type(JsonFieldType.NUMBER)
					.description("반려 동물 ID"),
			fieldWithPath("sessionId")
					.type(JsonFieldType.STRING)
					.description("세션 ID"),
			fieldWithPath("content")
					.type(JsonFieldType.STRING)
					.description("임시 저장 본문"),
			fieldWithPath("status")
					.type(JsonFieldType.STRING)
					.description("상태")
					.attributes(constraints("SAVE || DELETE")),
			fieldWithPath("createdAt")
					.type(JsonFieldType.STRING)
					.description("생성일"),
			fieldWithPath("updatedAt")
					.type(JsonFieldType.STRING)
					.description("수정일")
	);

	public static final Snippet TEMPORARY_CREATE_RESPONSE = responseFields(
			fieldWithPath("id")
					.type(JsonFieldType.NUMBER)
					.description("임시 저장 ID"),
			fieldWithPath("sessionId")
					.type(JsonFieldType.STRING)
					.description("세션 ID")
	);

	public static final Snippet TEMPORARY_SESSION_RESPONSE = responseFields(
			fieldWithPath("sessionId")
					.type(JsonFieldType.STRING)
					.description("세션 ID")
	);

}
