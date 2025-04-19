package com.example.booking_movie_ticket.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    MOVIE_NAME_INVALID(1002, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),

    FILE_EMPTY(2001, "Vui lòng chọn file để tải lên", HttpStatus.BAD_REQUEST),
    FILE_INVALID_TYPE(2002, "Định dạng file không được hỗ trợ. Chỉ chấp nhận các định dạng: JPG, PNG, JPEG", HttpStatus.NOT_FOUND)
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

}
