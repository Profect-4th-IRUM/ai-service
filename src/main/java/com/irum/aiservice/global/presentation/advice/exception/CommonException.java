package com.irum.aiservice.global.presentation.advice.exception;

import com.irum.aiservice.global.presentation.advice.exception.errorcode.AiErrorCode;
import com.irum.aiservice.global.presentation.advice.exception.errorcode.BaseErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public CommonException(AiErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
