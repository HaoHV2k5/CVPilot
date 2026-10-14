package com.cvpilot.common.exception;

import com.cvpilot.common.enums.ErrorCode;

public class ForbiddenException extends AppException {
    public ForbiddenException(String message) {
        super(ErrorCode.FORBIDDEN, message);
    }

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
