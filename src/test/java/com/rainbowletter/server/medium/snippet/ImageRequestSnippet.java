package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;

import org.springframework.restdocs.snippet.Snippet;

public class ImageRequestSnippet {

	public static final Snippet IMAGE_UPLOAD_HEADER = requestHeaders(
			headerWithName("Authorization")
					.description("액세스 토큰"),
			headerWithName("Content-Type")
					.description("명시하면 안됨")
	);

	public static final Snippet IMAGE_UPLOAD_MULTIPART = requestParts(
			partWithName("file")
					.description("업로드할 이미지")
					.attributes(constraints("10MB 이하의 이미지 파일"))
	);

	public static final Snippet PATH_PARAM_OBJECT_KEY = pathParameters(
			parameterWithName("dirName").description("이미지 object key"),
			parameterWithName("fileName").description("이미지 object key")
	);

}
