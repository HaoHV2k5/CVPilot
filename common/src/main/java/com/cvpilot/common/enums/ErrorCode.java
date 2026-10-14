package com.cvpilot.common.enums;

public enum ErrorCode {

    // Common HTTP & System errors
    SUCCESS(200, "Thao tác thành công"),
    BAD_REQUEST(400, "Yêu cầu không hợp lệ"),
    UNAUTHORIZED(401, "Chưa xác thực hoặc token không hợp lệ"),
    FORBIDDEN(403, "Không có quyền thực hiện thao tác này"),
    NOT_FOUND(404, "Không tìm thấy tài nguyên yêu cầu"),
    METHOD_NOT_ALLOWED(405, "Phương thức HTTP không được hỗ trợ"),
    CONFLICT(409, "Xung đột dữ liệu"),
    PAYLOAD_TOO_LARGE(413, "Dung lượng dữ liệu gửi lên vượt quá giới hạn"),
    UNSUPPORTED_MEDIA_TYPE(415, "Định dạng tệp không được hỗ trợ"),
    RATE_LIMIT_EXCEEDED(429, "Vượt quá giới hạn lượt gửi yêu cầu, vui lòng thử lại sau"),
    INTERNAL_SERVER_ERROR(500, "Đã xảy ra lỗi hệ thống nội bộ"),
    SERVICE_UNAVAILABLE(503, "Dịch vụ tạm thời không khả dụng"),

    // User & Authentication errors
    USER_NOT_FOUND(404, "Không tìm thấy người dùng"),
    USER_LOCKED(403, "Tài khoản người dùng đã bị khóa"),
    EMAIL_ALREADY_EXISTS(409, "Email này đã được sử dụng"),
    INVALID_CREDENTIALS(401, "Email hoặc mật khẩu không chính xác"),
    TOKEN_EXPIRED(401, "Phiên đăng nhập đã hết hạn, vui lòng đăng nhập lại"),

    // CV errors
    CV_NOT_FOUND(404, "Không tìm thấy CV"),
    CV_LIMIT_EXCEEDED(400, "Bạn đã đạt số lượng CV tối đa cho phép (5 CV)"),
    CV_FILE_INVALID(400, "Tệp CV không hợp lệ hoặc bị hỏng"),
    CV_FILE_TOO_LARGE(413, "Dung lượng tệp CV vượt quá giới hạn cho phép (tối đa 5MB)"),
    CV_NOT_READY(400, "CV chưa hoàn tất quá trình xử lý"),
    CV_SCAN_NOT_SUPPORTED(400, "CV dạng ảnh scan chưa được hỗ trợ, vui lòng tải CV dạng PDF/DOCX có văn bản"),

    // Career Roadmap & Jobs errors
    ROADMAP_NOT_FOUND(404, "Không tìm thấy lộ trình nghề nghiệp"),
    JOB_NOT_FOUND(404, "Không tìm thấy tin tuyển dụng"),

    // AI & Market Q&A errors
    QUOTA_EXCEEDED(429, "Bạn đã hết hạn mức câu hỏi trong ngày"),
    AI_SERVICE_ERROR(502, "Lỗi kết nối tới dịch vụ AI"),
    OUT_OF_SCOPE_QUESTION(400, "Câu hỏi ngoài phạm vi hỗ trợ của hệ thống");

    private final int httpStatus;
    private final String message;

    ErrorCode(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
