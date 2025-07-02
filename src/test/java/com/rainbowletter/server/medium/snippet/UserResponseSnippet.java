package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class UserResponseSnippet {

	public static final Snippet USER_INFORMATION_RESPONSE = responseFields(
			fieldWithPath("id")
					.type(JsonFieldType.NUMBER)
					.description("사용자 ID"),
			fieldWithPath("email")
					.type(JsonFieldType.STRING)
					.description("사용자 이메일"),
			fieldWithPath("phoneNumber")
					.type(JsonFieldType.STRING)
					.description("사용자 휴대폰 번호")
					.optional(),
			fieldWithPath("role")
					.type(JsonFieldType.STRING)
					.description("사용자 권한 정보")
					.attributes(constraints("ROLE_USER | ROLE_ADMIN")),
			fieldWithPath("provider")
					.type(JsonFieldType.STRING)
					.description("사용자 로그인 타입")
					.attributes(constraints("NONE | GOOGLE | NAVER | KAKAO")),
			fieldWithPath("lastLoggedIn")
					.type(JsonFieldType.STRING)
					.description("사용자 마지막 로그인 시간")
					.optional(),
			fieldWithPath("lastChangedPassword")
					.type(JsonFieldType.STRING)
					.description("사용자 마지막 비밀번호 변경일"),
			fieldWithPath("createdAt")
					.type(JsonFieldType.STRING)
					.description("사용자 생성일")
	);

	public static final Snippet USER_VERIFY_RESPONSE = responseFields(
			fieldWithPath("email")
					.type(JsonFieldType.STRING)
					.description("사용자 이메일"),
			fieldWithPath("role")
					.type(JsonFieldType.STRING)
					.description("사용자 권한 정보")
					.attributes(constraints("ROLE_USER | ROLE_ADMIN"))
	);

	public static final Snippet USER_CREATE_RESPONSE_HEADER = responseHeaders(
			headerWithName(HttpHeaders.LOCATION).description("생성된 사용자 ID")
	);

	public static final Snippet USER_LOGIN_RESPONSE = responseFields(
			fieldWithPath("token")
					.type(JsonFieldType.STRING)
					.description("액세스 토큰")
	);

}
