package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class FaqRequestSnippet {

	public static final Snippet FAQ_CREATE_REQUEST = requestFields(
			fieldWithPath("summary")
					.type(JsonFieldType.STRING)
					.description("제목")
					.attributes(constraints("30자 이하")),
			fieldWithPath("detail")
					.type(JsonFieldType.STRING)
					.description("본문")
					.attributes(constraints("공백 불가"))
	);

	public static final Snippet FAQ_PATH_VARIABLE_ID = pathParameters(
			parameterWithName("id").description("Faq ID")
	);

	public static final Snippet FAQ_UPDATE_REQUEST = requestFields(
			fieldWithPath("summary")
					.type(JsonFieldType.STRING)
					.description("제목")
					.attributes(constraints("30자 이하")),
			fieldWithPath("detail")
					.type(JsonFieldType.STRING)
					.description("본문")
					.attributes(constraints("공백 불가"))
	);

	public static final Snippet FAQ_SWITCH_SEQUENCE_REQUEST = requestFields(
			fieldWithPath("id")
					.type(JsonFieldType.NUMBER)
					.description("순번을 변경할 Faq ID"),
			fieldWithPath("targetId")
					.type(JsonFieldType.NUMBER)
					.description("바꾸고자 하는 순번의 Faq ID")
	);

}
