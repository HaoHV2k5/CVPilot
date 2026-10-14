package com.cvpilot.common.dto;

import com.cvpilot.common.enums.ErrorCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiResponseTest {

    @Test
    void testSuccessResponse() {
        ApiResponse<String> response = ApiResponse.success("test-data");
        assertTrue(response.isSuccess());
        assertEquals(200, response.getCode());
        assertEquals("test-data", response.getData());
        assertNotNull(response.getTimestamp());
        assertNull(response.getErrorCode());
    }

    @Test
    void testErrorResponseWithCode() {
        ApiResponse<Void> response = ApiResponse.error(ErrorCode.CV_NOT_FOUND);
        assertFalse(response.isSuccess());
        assertEquals(404, response.getCode());
        assertEquals("CV_NOT_FOUND", response.getErrorCode());
        assertEquals(ErrorCode.CV_NOT_FOUND.getMessage(), response.getMessage());
        assertNull(response.getData());
    }

    @Test
    void testErrorResponseWithCustomMessage() {
        ApiResponse<Void> response = ApiResponse.error(ErrorCode.BAD_REQUEST, "Custom validation message");
        assertFalse(response.isSuccess());
        assertEquals(400, response.getCode());
        assertEquals("BAD_REQUEST", response.getErrorCode());
        assertEquals("Custom validation message", response.getMessage());
    }
}
