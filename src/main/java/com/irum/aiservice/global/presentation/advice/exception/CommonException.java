package com.irum.aiservice.global.presentation.advice.exception;

import com.irum.aiservice.global.presentation.advice.exception.errorcode.BaseErrorCode;
import com.irum.aiservice.global.presentation.advice.exception.errorcode.CategoryErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {
    private final BaseErrorCode errorCode;

    public CommonException(CategoryErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
