package com.example.booking_movie_ticket.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    FILE_EMPTY(2001, "Vui lòng chọn file để tải lên", HttpStatus.BAD_REQUEST),
    FILE_INVALID_TYPE(2002, "Định dạng file không được hỗ trợ. Chỉ chấp nhận các định dạng: JPG, PNG, JPEG", HttpStatus.NOT_FOUND),

    MOVIE_NAME_BLANK(3000, "Tên phim không được bỏ trống", HttpStatus.BAD_REQUEST),
    DIRECTOR_NAME_BLANK(3001, "Tên đạo diễn không được bỏ trống", HttpStatus.BAD_REQUEST),
    DATA_TOO_LONG(3002, "Dữ liệu nhập vào vượt quá độ dài cho phép", HttpStatus.BAD_REQUEST),
    POSTER_BLANK(3003, "Poster không được bỏ trống", HttpStatus.BAD_REQUEST),
    TRAILER_URL_BLANK(3004, "TrailerUrl không được bỏ trống ", HttpStatus.BAD_REQUEST),
    GENRE_BLANK(3005, "Thể loại không được bỏ trống ", HttpStatus.BAD_REQUEST),
    RELEASE_DATE_INVALID(3006, "Ngày khởi chiếu không hợp lệ", HttpStatus.BAD_REQUEST),

    ROOM_NOT_FREE(4000,"Phòng chiếu đã được sử dụng vào thời gian bạn chọn", HttpStatus.BAD_REQUEST)
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
