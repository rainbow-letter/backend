package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class LetterRequestSnippet {

	public static final Snippet LETTER_QUERY_PARAM_PET_ID = queryParameters(
			parameterWithName("pet").description("반려 동물 ID")
	);

	public static final Snippet LETTER_BOX_QUERY_PARAMS = queryParameters(
			parameterWithName("pet")
					.description("반려 동물 ID"),
			parameterWithName("start")
					.description("검색 시작 날짜 (end 파라미터와 같이 사용)")
					.attributes(constraints("yyyy-MM-dd"))
					.optional(),
			parameterWithName("end")
					.description("검색 종료 날짜 (start 파라미터와 같이 사용)")
					.attributes(constraints("yyyy-MM-dd"))
					.optional()
	);

	public static final Snippet LETTER_ADMIN_QUERY_PARAMS = queryParameters(
			parameterWithName("start")
					.description("검색 시작 날짜")
					.attributes(constraints("yyyy-MM-dd")),
			parameterWithName("end")
					.description("검색 종료 날짜")
					.attributes(constraints("yyyy-MM-dd")),
			parameterWithName("status")
					.description("답장 발송 여부")
					.attributes(constraints("CHAT_GPT || REPLY"))
					.optional(),
			parameterWithName("email")
					.description("검색 이메일")
					.optional(),
			parameterWithName("inspect")
					.description("검수 여부")
					.attributes(constraints("true || false"))
					.optional(),
			parameterWithName("page")
					.description("페이지 번호")
					.attributes(constraints("0부터 시작")),
			parameterWithName("size")
					.description("페이지 데이터 조회 개수")
	);

	public static final Snippet LETTER_ADMIN_DETAIL_QUERY_PARAMS = queryParameters(
			parameterWithName("user")
					.description("사용자 ID")
	);

	public static final Snippet LETTER_PATH_VARIABLE_ID = pathParameters(
			parameterWithName("id").description("편지 ID")
	);

	public static final Snippet LETTER_PATH_VARIABLE_SHARE_LINK = pathParameters(
			parameterWithName("shareLink").description("편지 공유 링크")
	);

	public static final Snippet LETTER_CREATE_REQUEST = requestFields(
			fieldWithPath("summary")
					.type(JsonFieldType.STRING)
					.description("편지 제목")
					.attributes(constraints("20자 이내")),
			fieldWithPath("content")
					.type(JsonFieldType.STRING)
					.description("편지 본문")
					.attributes(constraints("1000자 이내")),
			fieldWithPath("image")
					.type(JsonFieldType.STRING)
					.description("편지 이미지")
					.attributes(constraints("이미지 objectKey"))
					.optional()
	);

}
