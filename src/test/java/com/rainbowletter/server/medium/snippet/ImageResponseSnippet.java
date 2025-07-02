package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class ImageResponseSnippet {

	public static final Snippet IMAGE_UPLOAD_RESPONSE = responseFields(
			fieldWithPath("objectKey")
					.type(JsonFieldType.STRING)
					.description("이미지 objectKey")
					.attributes(constraints("dirName/fileName 형식"))
	);

}
