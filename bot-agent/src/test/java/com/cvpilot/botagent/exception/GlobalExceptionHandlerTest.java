package com.cvpilot.botagent.exception;

import com.cvpilot.common.dto.ApiResponse;
import com.cvpilot.common.enums.ErrorCode;
import com.cvpilot.common.exception.AppException;
import com.cvpilot.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleAppException_shouldReturnCorrectStatusAndBody() {
        AppException ex = new ResourceNotFoundException("CV không tồn tại với ID chỉ định");
        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleAppException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals(404, response.getBody().getCode());
        assertEquals("NOT_FOUND", response.getBody().getErrorCode());
        assertEquals("CV không tồn tại với ID chỉ định", response.getBody().getMessage());
    }

    @Test
    void handleGenericException_shouldReturnInternalServerError() {
        Exception ex = new RuntimeException("Unexpected DB failure");
        ResponseEntity<ApiResponse<Void>> response = exceptionHandler.handleGenericException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isSuccess());
        assertEquals(500, response.getBody().getCode());
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), response.getBody().getMessage());
    }
}
