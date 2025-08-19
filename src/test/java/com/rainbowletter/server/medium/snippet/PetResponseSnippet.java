package com.rainbowletter.server.medium.snippet;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class PetResponseSnippet {

	public static final Snippet PET_RESPONSES = responseFields(
			fieldWithPath("pets[].id")
					.type(JsonFieldType.NUMBER)
					.description("반려동물 ID"),
			fieldWithPath("pets[].userId")
					.type(JsonFieldType.NUMBER)
					.description("사용자 ID"),
			fieldWithPath("pets[].name")
					.type(JsonFieldType.STRING)
					.description("아이의 이름"),
			fieldWithPath("pets[].species")
					.type(JsonFieldType.STRING)
					.description("아이의 종류"),
			fieldWithPath("pets[].owner")
					.type(JsonFieldType.STRING)
					.description("주인을 부르는 호칭"),
			fieldWithPath("pets[].personalities")
					.type(JsonFieldType.ARRAY)
					.description("아이의 성격"),
			fieldWithPath("pets[].deathAnniversary")
					.type(JsonFieldType.STRING)
					.description("아이가 떠난 날")
					.optional(),
			fieldWithPath("pets[].image")
					.type(JsonFieldType.STRING)
					.description("이미지의 objectKey")
					.optional(),
			fieldWithPath("pets[].createdAt")
					.type(JsonFieldType.STRING)
					.description("생성일"),
			fieldWithPath("pets[].updatedAt")
					.type(JsonFieldType.STRING)
					.description("수정일")
	);

	public static final Snippet PET_RESPONSE = responseFields(
			fieldWithPath("id")
					.type(JsonFieldType.NUMBER)
					.description("반려동물 ID"),
			fieldWithPath("userId")
					.type(JsonFieldType.NUMBER)
					.description("사용자 ID"),
			fieldWithPath("name")
					.type(JsonFieldType.STRING)
					.description("아이의 이름"),
			fieldWithPath("species")
					.type(JsonFieldType.STRING)
					.description("아이의 종류"),
			fieldWithPath("owner")
					.type(JsonFieldType.STRING)
					.description("주인을 부르는 호칭"),
			fieldWithPath("personalities")
					.type(JsonFieldType.ARRAY)
					.description("아이의 성격"),
			fieldWithPath("deathAnniversary")
					.type(JsonFieldType.STRING)
					.description("아이가 떠난 날")
					.optional(),
			fieldWithPath("image")
					.type(JsonFieldType.STRING)
					.description("이미지의 objectKey")
					.optional(),
			fieldWithPath("createdAt")
					.type(JsonFieldType.STRING)
					.description("생성일"),
			fieldWithPath("updatedAt")
					.type(JsonFieldType.STRING)
					.description("수정일")
	);

	public static final Snippet PET_DASHBOARD_RESPONSES = responseFields(
			fieldWithPath("pets[].id")
					.type(JsonFieldType.NUMBER)
					.description("반려동물 ID"),
			fieldWithPath("pets[].name")
					.type(JsonFieldType.STRING)
					.description("아이의 이름"),
			fieldWithPath("pets[].letterCount")
					.type(JsonFieldType.NUMBER)
					.description("보낸 편지 수"),
			fieldWithPath("pets[].image")
					.type(JsonFieldType.STRING)
					.description("이미지의 objectKey")
					.optional(),
			fieldWithPath("pets[].deathAnniversary")
					.type(JsonFieldType.STRING)
					.description("아이가 떠난 날")
					.optional()
	);

	public static final Snippet PET_CREATE_RESPONSE_HEADER = responseHeaders(
			headerWithName(HttpHeaders.LOCATION).description("생성된 반려동물 ID")
	);

}
