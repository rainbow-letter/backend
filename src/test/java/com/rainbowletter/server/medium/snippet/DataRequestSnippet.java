package com.rainbowletter.server.medium.snippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class DataRequestSnippet {

	public static final Snippet DATA_CREATE_REQUEST = requestFields(
			fieldWithPath("event")
					.type(JsonFieldType.STRING)
					.description("이벤트 명")
	);

}
