package com.cvpilot.common.exception;

import com.cvpilot.common.enums.ErrorCode;

public class BadRequestException extends AppException {
    public BadRequestException(String message) {
        super(ErrorCode.BAD_REQUEST, message);
    }

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BadRequestException(ErrorCode errorCode, String detailMessage) {
        super(errorCode, detailMessage);
    }
}
