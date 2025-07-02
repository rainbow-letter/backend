package com.rainbowletter.server.medium.snippet;

import static com.rainbowletter.server.medium.RestDocsUtils.constraints;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public class LetterResponseSnippet {

    public static final Snippet LETTER_CREATE_RESPONSE_HEADER = responseHeaders(
        headerWithName(HttpHeaders.LOCATION).description("생성된 편지 ID")
    );

    public static final Snippet LETTER_BOX_RESPONSE = responseFields(
        fieldWithPath("letters[].id")
            .type(JsonFieldType.NUMBER)
            .description("편지 ID"),
        fieldWithPath("letters[].number")
            .type(JsonFieldType.NUMBER)
            .description("편지 회차"),
        fieldWithPath("letters[].summary")
            .type(JsonFieldType.STRING)
            .description("편지 제목"),
        fieldWithPath("letters[].status")
            .type(JsonFieldType.STRING)
            .description("편지 답장 여부")
            .attributes(constraints("REQUEST || RESPONSE")),
        fieldWithPath("letters[].petName")
            .type(JsonFieldType.STRING)
            .description("편지를 받는 반려 동물 이름"),
        fieldWithPath("letters[].readStatus")
            .type(JsonFieldType.STRING)
            .description("답장 읽음 여부")
            .attributes(constraints("UNREAD || READ"))
            .optional(),
        fieldWithPath("letters[].createdAt")
            .type(JsonFieldType.STRING)
            .description("편지 작성일")
    );

    public static final Snippet LETTER_RESPONSE = responseFields(
        fieldWithPath("pet.id")
            .type(JsonFieldType.NUMBER)
            .description("반려동물 ID"),
        fieldWithPath("pet.userId")
            .type(JsonFieldType.NUMBER)
            .description("사용자 ID"),
        fieldWithPath("pet.name")
            .type(JsonFieldType.STRING)
            .description("아이의 이름"),
        fieldWithPath("pet.species")
            .type(JsonFieldType.STRING)
            .description("아이의 종류"),
        fieldWithPath("pet.owner")
            .type(JsonFieldType.STRING)
            .description("주인을 부르는 호칭"),
        fieldWithPath("pet.personalities")
            .type(JsonFieldType.ARRAY)
            .description("아이의 성격"),
        fieldWithPath("pet.deathAnniversary")
            .type(JsonFieldType.STRING)
            .description("아이가 떠난 날")
            .optional(),
        fieldWithPath("pet.image")
            .type(JsonFieldType.STRING)
            .description("이미지의 objectKey")
            .optional(),
        fieldWithPath("pet.createdAt")
            .type(JsonFieldType.STRING)
            .description("생성일"),
        fieldWithPath("pet.updatedAt")
            .type(JsonFieldType.STRING)
            .description("수정일"),
        fieldWithPath("letter.id")
            .type(JsonFieldType.NUMBER)
            .description("편지 ID"),
        fieldWithPath("letter.userId")
            .type(JsonFieldType.NUMBER)
            .description("사용자 ID"),
        fieldWithPath("letter.petId")
            .type(JsonFieldType.NUMBER)
            .description("반려동물 ID"),
        fieldWithPath("letter.number")
            .type(JsonFieldType.NUMBER)
            .description("편지 회차"),
        fieldWithPath("letter.summary")
            .type(JsonFieldType.STRING)
            .description("편지 제목"),
        fieldWithPath("letter.content")
            .type(JsonFieldType.STRING)
            .description("편지 본문"),
        fieldWithPath("letter.shareLink")
            .type(JsonFieldType.STRING)
            .description("공유 링크 UUID"),
        fieldWithPath("letter.image")
            .type(JsonFieldType.STRING)
            .description("이미지의 objectKey")
            .optional(),
        fieldWithPath("letter.status")
            .type(JsonFieldType.STRING)
            .description("편지 답장 여부")
            .attributes(constraints("REQUEST || RESPONSE")),
        fieldWithPath("letter.createdAt")
            .type(JsonFieldType.STRING)
            .description("생성일"),
        fieldWithPath("letter.updatedAt")
            .type(JsonFieldType.STRING)
            .description("수정일"),
        fieldWithPath("reply")
            .type(JsonFieldType.OBJECT)
            .description("답장 정보 객체")
            .optional(),
        fieldWithPath("reply.id")
            .type(JsonFieldType.NUMBER)
            .description("답장 ID"),
        fieldWithPath("reply.petId")
            .type(JsonFieldType.NUMBER)
            .description("반려동물 ID"),
        fieldWithPath("reply.letterId")
            .type(JsonFieldType.NUMBER)
            .description("편지 ID"),
        fieldWithPath("reply.summary")
            .type(JsonFieldType.STRING)
            .description("답장 제목"),
        fieldWithPath("reply.content")
            .type(JsonFieldType.STRING)
            .description("답장 본문"),
        fieldWithPath("reply.promptType")
            .type(JsonFieldType.STRING)
            .description("답장 GPT 프롬프트 타입")
            .attributes(constraints("A || B")),
        fieldWithPath("reply.inspection")
            .type(JsonFieldType.BOOLEAN)
            .description("답장 검수 여부"),
        fieldWithPath("reply.inspectionTime")
            .type(JsonFieldType.STRING)
            .description("답장 검수일")
            .optional(),
        fieldWithPath("reply.status")
            .type(JsonFieldType.STRING)
            .description("답장 상태")
            .attributes(constraints("CHAT_GPT || REPLY")),
        fieldWithPath("reply.submitTime")
            .type(JsonFieldType.STRING)
            .description("답장 발송일")
            .optional(),
        fieldWithPath("reply.readStatus")
            .type(JsonFieldType.STRING)
            .description("답장 읽음 상태")
            .attributes(constraints("UNREAD || READ")),
        fieldWithPath("reply.createdAt")
            .type(JsonFieldType.STRING)
            .description("생성일"),
        fieldWithPath("reply.updatedAt")
            .type(JsonFieldType.STRING)
            .description("수정일")
    );

    public static final Snippet LETTER_ADMIN_PAGE_RESPONSE = responseFields(
        fieldWithPath("content[].id")
            .type(JsonFieldType.NUMBER)
            .description("편지 ID"),
        fieldWithPath("content[].userId")
            .type(JsonFieldType.NUMBER)
            .description("사용자 ID"),
        fieldWithPath("content[].petId")
            .type(JsonFieldType.NUMBER)
            .description("반려동물 ID"),
        fieldWithPath("content[].replyId")
            .type(JsonFieldType.NUMBER)
            .description("답장 ID")
            .optional(),
        fieldWithPath("content[].email")
            .type(JsonFieldType.STRING)
            .description("사용자 이메일"),
        fieldWithPath("content[].count")
            .type(JsonFieldType.NUMBER)
            .description("사용자의 총 편지 수"),
        fieldWithPath("content[].summary")
            .type(JsonFieldType.STRING)
            .description("편지 제목"),
        fieldWithPath("content[].content")
            .type(JsonFieldType.STRING)
            .description("편지 본문"),
        fieldWithPath("content[].promptType")
            .type(JsonFieldType.STRING)
            .description("프롬프트 타입 A || B")
            .optional(),
        fieldWithPath("content[].inspection")
            .type(JsonFieldType.BOOLEAN)
            .description("답장 검수 여부")
            .optional(),
        fieldWithPath("content[].inspectionTime")
            .type(JsonFieldType.STRING)
            .description("답장 검수 시간")
            .optional(),
        fieldWithPath("content[].status")
            .type(JsonFieldType.STRING)
            .description("답장 상태 CHAT_GPT || REPLY")
            .optional(),
        fieldWithPath("content[].submitTime")
            .type(JsonFieldType.STRING)
            .description("답장 시간")
            .optional(),
        fieldWithPath("content[].createdAt")
            .type(JsonFieldType.STRING)
            .description("편지 작성일"),
        fieldWithPath("content[].updatedAt")
            .type(JsonFieldType.STRING)
            .description("편지 수정일"),
        fieldWithPath("page.size")
            .type(JsonFieldType.NUMBER)
            .description("페이지 데이터 조회 개수"),
        fieldWithPath("page.number")
            .type(JsonFieldType.NUMBER)
            .description("현재 페이지 번호"),
        fieldWithPath("page.totalElements")
            .type(JsonFieldType.NUMBER)
            .description("총 데이터 개수"),
        fieldWithPath("page.totalPages")
            .type(JsonFieldType.NUMBER)
            .description("총 페이지 수")
    );

    public static final Snippet LETTER_ADMIN_DETAIL_RESPONSE = responseFields(
        fieldWithPath("user.id")
            .type(JsonFieldType.NUMBER)
            .description("사용자 ID"),
        fieldWithPath("user.email")
            .type(JsonFieldType.STRING)
            .description("사용자 이메일"),
        fieldWithPath("user.phoneNumber")
            .type(JsonFieldType.STRING)
            .description("사용자 휴대폰 번호"),
        fieldWithPath("user.role")
            .type(JsonFieldType.STRING)
            .description("사용자 권한 정보")
            .attributes(constraints("ROLE_USER | ROLE_ADMIN")),
        fieldWithPath("user.provider")
            .type(JsonFieldType.STRING)
            .description("사용자 로그인 타입")
            .attributes(constraints("NONE | GOOGLE | NAVER | KAKAO")),
        fieldWithPath("user.count")
            .type(JsonFieldType.NUMBER)
            .description("사용자 총 편지 개수"),
        fieldWithPath("user.lastLoggedIn")
            .type(JsonFieldType.STRING)
            .description("사용자 마지막 로그인 시간")
            .optional(),
        fieldWithPath("user.lastChangedPassword")
            .type(JsonFieldType.STRING)
            .description("사용자 마지막 비밀번호 변경일"),
        fieldWithPath("user.createdAt")
            .type(JsonFieldType.STRING)
            .description("사용자 생성일"),
        fieldWithPath("pet.id")
            .type(JsonFieldType.NUMBER)
            .description("반려동물 ID"),
        fieldWithPath("pet.userId")
            .type(JsonFieldType.NUMBER)
            .description("사용자 ID"),
        fieldWithPath("pet.name")
            .type(JsonFieldType.STRING)
            .description("아이의 이름"),
        fieldWithPath("pet.species")
            .type(JsonFieldType.STRING)
            .description("아이의 종류"),
        fieldWithPath("pet.owner")
            .type(JsonFieldType.STRING)
            .description("주인을 부르는 호칭"),
        fieldWithPath("pet.personalities")
            .type(JsonFieldType.ARRAY)
            .description("아이의 성격"),
        fieldWithPath("pet.deathAnniversary")
            .type(JsonFieldType.STRING)
            .description("아이가 떠난 날")
            .optional(),
        fieldWithPath("pet.image")
            .type(JsonFieldType.STRING)
            .description("이미지의 objectKey")
            .optional(),
        fieldWithPath("pet.createdAt")
            .type(JsonFieldType.STRING)
            .description("생성일"),
        fieldWithPath("pet.updatedAt")
            .type(JsonFieldType.STRING)
            .description("수정일"),
        fieldWithPath("letter.id")
            .type(JsonFieldType.NUMBER)
            .description("편지 ID"),
        fieldWithPath("letter.userId")
            .type(JsonFieldType.NUMBER)
            .description("사용자 ID"),
        fieldWithPath("letter.petId")
            .type(JsonFieldType.NUMBER)
            .description("반려동물 ID"),
        fieldWithPath("letter.number")
            .type(JsonFieldType.NUMBER)
            .description("편지 회차"),
        fieldWithPath("letter.summary")
            .type(JsonFieldType.STRING)
            .description("편지 제목"),
        fieldWithPath("letter.content")
            .type(JsonFieldType.STRING)
            .description("편지 본문"),
        fieldWithPath("letter.shareLink")
            .type(JsonFieldType.STRING)
            .description("공유 링크 UUID"),
        fieldWithPath("letter.image")
            .type(JsonFieldType.STRING)
            .description("이미지의 objectKey")
            .optional(),
        fieldWithPath("letter.status")
            .type(JsonFieldType.STRING)
            .description("편지 답장 여부")
            .attributes(constraints("REQUEST || RESPONSE")),
        fieldWithPath("letter.createdAt")
            .type(JsonFieldType.STRING)
            .description("생성일"),
        fieldWithPath("letter.updatedAt")
            .type(JsonFieldType.STRING)
            .description("수정일"),
        fieldWithPath("reply")
            .type(JsonFieldType.OBJECT)
            .description("답장 정보 객체")
            .optional(),
        fieldWithPath("reply.id")
            .type(JsonFieldType.NUMBER)
            .description("답장 ID"),
        fieldWithPath("reply.petId")
            .type(JsonFieldType.NUMBER)
            .description("반려동물 ID"),
        fieldWithPath("reply.letterId")
            .type(JsonFieldType.NUMBER)
            .description("편지 ID"),
        fieldWithPath("reply.summary")
            .type(JsonFieldType.STRING)
            .description("답장 제목"),
        fieldWithPath("reply.content")
            .type(JsonFieldType.STRING)
            .description("답장 본문"),
        fieldWithPath("reply.promptA")
            .type(JsonFieldType.STRING)
            .description("프롬프트 A"),
        fieldWithPath("reply.promptB")
            .type(JsonFieldType.STRING)
            .description("프롬프트 B"),
        fieldWithPath("reply.promptType")
            .type(JsonFieldType.STRING)
            .description("답장 GPT 프롬프트 타입")
            .attributes(constraints("A || B")),
        fieldWithPath("reply.inspection")
            .type(JsonFieldType.BOOLEAN)
            .description("답장 검수 여부"),
        fieldWithPath("reply.inspectionTime")
            .type(JsonFieldType.STRING)
            .description("답장 검수일")
            .optional(),
        fieldWithPath("reply.status")
            .type(JsonFieldType.STRING)
            .description("답장 상태")
            .attributes(constraints("CHAT_GPT || REPLY")),
        fieldWithPath("reply.submitTime")
            .type(JsonFieldType.STRING)
            .description("답장 발송일")
            .optional(),
        fieldWithPath("reply.readStatus")
            .type(JsonFieldType.STRING)
            .description("답장 읽음 상태")
            .attributes(constraints("UNREAD || READ")),
        fieldWithPath("reply.createdAt")
            .type(JsonFieldType.STRING)
            .description("생성일"),
        fieldWithPath("reply.updatedAt")
            .type(JsonFieldType.STRING)
            .description("수정일"),
        fieldWithPath("recent[].id")
            .type(JsonFieldType.NUMBER)
            .description("최근 편지 ID"),
        fieldWithPath("recent[].userId")
            .type(JsonFieldType.NUMBER)
            .description("최근 편지 사용자 ID"),
        fieldWithPath("recent[].petId")
            .type(JsonFieldType.NUMBER)
            .description("최근 편지 반려동물 ID"),
        fieldWithPath("recent[].number")
            .type(JsonFieldType.NUMBER)
            .description("편지 회차"),
        fieldWithPath("recent[].petName")
            .type(JsonFieldType.STRING)
            .description("최근 편지 반려동물 이름"),
        fieldWithPath("recent[].summary")
            .type(JsonFieldType.STRING)
            .description("최근 편지 제목"),
        fieldWithPath("recent[].content")
            .type(JsonFieldType.STRING)
            .description("최근 편지 본문"),
        fieldWithPath("recent[].inspection")
            .type(JsonFieldType.BOOLEAN)
            .description("최근 편지 답장 검수 여부"),
        fieldWithPath("recent[].status")
            .type(JsonFieldType.STRING)
            .description("최근 편지 답장 상태")
            .attributes(constraints("CHAT_GPT || REPLY")),
        fieldWithPath("recent[].createdAt")
            .type(JsonFieldType.STRING)
            .description("최근 편지 생성일"),
        fieldWithPath("recent[].updatedAt")
            .type(JsonFieldType.STRING)
            .description("최근 편지 수정일")
    );

}
