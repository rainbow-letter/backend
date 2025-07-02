package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class PetRequestSnippet {

	public static final Snippet PET_PATH_VARIABLE_ID = pathParameters(
			parameterWithName("id").description("반려동물 ID")
	);

	public static final Snippet PET_CREATE_REQUEST = requestFields(
			fieldWithPath("name")
					.type(JsonFieldType.STRING)
					.description("반려동물 이름")
					.attributes(constraints("20자 이내")),
			fieldWithPath("species")
					.type(JsonFieldType.STRING)
					.description("반려동물 종류")
					.attributes(constraints("10자 이내")),
			fieldWithPath("owner")
					.type(JsonFieldType.STRING)
					.description("주인을 부르는 호칭")
					.attributes(constraints("10자 이내")),
			fieldWithPath("personalities")
					.type(JsonFieldType.ARRAY)
					.description("반려동물 성격")
					.attributes(constraints("10자 이내 문자열 배열")),
			fieldWithPath("deathAnniversary")
					.type(JsonFieldType.STRING)
					.description("아이가 떠난 날")
					.attributes(constraints("yyyy-MM-dd"))
					.optional(),
			fieldWithPath("image")
					.type(JsonFieldType.STRING)
					.description("반려동물 이미지")
					.attributes(constraints("이미지 objectKey"))
					.optional()
	);

	public static final Snippet PET_UPDATE_REQUEST = requestFields(
			fieldWithPath("name")
					.type(JsonFieldType.STRING)
					.description("반려동물 이름")
					.attributes(constraints("20자 이내")),
			fieldWithPath("species")
					.type(JsonFieldType.STRING)
					.description("반려동물 종류")
					.attributes(constraints("10자 이내")),
			fieldWithPath("owner")
					.type(JsonFieldType.STRING)
					.description("주인을 부르는 호칭")
					.attributes(constraints("10자 이내")),
			fieldWithPath("personalities")
					.type(JsonFieldType.ARRAY)
					.description("반려동물 성격")
					.attributes(constraints("10자 이내 문자열 배열")),
			fieldWithPath("deathAnniversary")
					.type(JsonFieldType.STRING)
					.description("아이가 떠난 날")
					.attributes(constraints("yyyy-MM-dd"))
					.optional(),
			fieldWithPath("image")
					.type(JsonFieldType.STRING)
					.description("반려동물 이미지")
					.attributes(constraints("이미지 objectKey"))
					.optional()
	);

}
