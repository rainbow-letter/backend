package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class TemporaryRequestSnippet {

	public static final Snippet TEMPORARY_PATH_VARIABLE_ID = pathParameters(
			parameterWithName("id").description("임시 저장 ID")
	);

	public static final Snippet TEMPORARY_PET_QUERY_PARAMS = queryParameters(
			parameterWithName("pet")
					.description("반려동물 ID")
	);

	public static final Snippet TEMPORARY_UPDATE_REQUEST = requestFields(
			fieldWithPath("petId")
					.type(JsonFieldType.NUMBER)
					.description("반려동물 ID"),
			fieldWithPath("content")
					.type(JsonFieldType.STRING)
					.description("수정 임시 저장 본문")
					.attributes(constraints("1000자 이내"))
	);

}
