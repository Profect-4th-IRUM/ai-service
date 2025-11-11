package com.irum.aiservice.global.advice.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AiErrorCode implements BaseErrorCode {
    AI_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "AI 설명 생성에 실패했습니다."),
    AI_INVALID_PROMPT(HttpStatus.BAD_REQUEST, "AI 요청 프롬프트가 유효하지 않습니다."),
    AI_API_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "AI 서비스가 현재 이용 불가 상태입니다."),
    AI_RESPONSE_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "AI 응답이 비어 있습니다."),
    AI_TIMEOUT(HttpStatus.GATEWAY_TIMEOUT, "AI API 호출이 시간 초과되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public String errorClassName() {
        return this.name();
    }
}
