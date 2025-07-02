package com.rainbowletter.server.medium.snippet;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class FaqResponseSnippet {

	public static final Snippet VISIBILITY_FAQ_RESPONSES = responseFields(
			fieldWithPath("faqs[].id")
					.type(JsonFieldType.NUMBER)
					.description("Faq ID"),
			fieldWithPath("faqs[].summary")
					.type(JsonFieldType.STRING)
					.description("제목"),
			fieldWithPath("faqs[].detail")
					.type(JsonFieldType.STRING)
					.description("상세 내용")
	);

	public static final Snippet ADMIN_FAQ_RESPONSES = responseFields(
			fieldWithPath("faqs[].id")
					.type(JsonFieldType.NUMBER)
					.description("Faq ID"),
			fieldWithPath("faqs[].summary")
					.type(JsonFieldType.STRING)
					.description("제목"),
			fieldWithPath("faqs[].detail")
					.type(JsonFieldType.STRING)
					.description("상세 내용"),
			fieldWithPath("faqs[].visibility")
					.type(JsonFieldType.BOOLEAN)
					.description("사용자에게 보이기 여부"),
			fieldWithPath("faqs[].sequence")
					.type(JsonFieldType.NUMBER)
					.description("정렬 순번"),
			fieldWithPath("faqs[].createdAt")
					.type(JsonFieldType.STRING)
					.description("생성일"),
			fieldWithPath("faqs[].updatedAt")
					.type(JsonFieldType.STRING)
					.description("수정일")
	);

	public static final Snippet FAQ_CREATE_RESPONSE_HEADER = responseHeaders(
			headerWithName(HttpHeaders.LOCATION).description("생성된 Faq ID")
	);

}
