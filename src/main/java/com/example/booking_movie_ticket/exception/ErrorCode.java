package com.example.booking_movie_ticket.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    USER_EXISTED(1001, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1002, "User không tồn tại", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(1003, "Tên đăng nhập đã tồn tại ", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1004, "Email này đã được sử dụng", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTED(1005, "Số điện thoại đã được đăng ký", HttpStatus.BAD_REQUEST),
    DATA_UNIQUE(1006, "Thông tin đăng ký đã tồn tại", HttpStatus.BAD_REQUEST),
    USERNAME_BLANK(1007, "Tên đăng nhập không được bỏ trống", HttpStatus.BAD_REQUEST),
    FULL_NAME_BLANK(1008, "Họ tên không được bỏ trống", HttpStatus.BAD_REQUEST),
    EMAIL_BLANK(1009, "Email không được bỏ trống", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_BLANK(1010, "Số điện thoại không được bỏ trống", HttpStatus.BAD_REQUEST),
    PASSWORD_BLANK(1011, "Mật khẩu không được bỏ trống", HttpStatus.BAD_REQUEST),
    BIRTHDAY_BLANK(1012, "Ngày sinh không được bỏ trống", HttpStatus.BAD_REQUEST),
    USERNAME_LENGTH_INVALID(1013, "Tên đăng nhập phải từ 4 ký tự trở lên", HttpStatus.BAD_REQUEST),
    PASSWORD_LENGTH_INVALID(1014, "Mật khẩu phải từ 6 ký tự trở lên", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1015, "Email không hợp lệ", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(1016, "Số điện thoại không hợp lệ", HttpStatus.BAD_REQUEST),
    BIRTHDAY_INVALID(1017, "Ngày sinh không hợp lệ", HttpStatus.BAD_REQUEST),

    INVALID_LOGIN_CREDENTIALS_ERROR(1090, "Thông tin đăng nhập không chính xác", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN_ERROR(1091, "Token không hợp lệ (hết hạn, sai định dạng, hoặc thiếu quyền truy cập)", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_NOT_PRESENT_IN_COOKIES(1092, "Refresh token không có trong cookies", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN(1093, "Refresh token không hợp lệ", HttpStatus.BAD_REQUEST),

    ROLE_NAME_BLANK(1100, "Tên vai trò không được bỏ trống", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1101, "Vai trò đã tồn tại", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(1102, "Vai trò không tồn tại", HttpStatus.NOT_FOUND),



    PERMISSION_NAME_BLANK(1200, "Tên quyền hạn không được bỏ trống", HttpStatus.BAD_REQUEST),
    API_PATH_BLANK(1201, "API_PATH không được bỏ trống", HttpStatus.BAD_REQUEST),
    METHOD_BLANK(1202, "METHOD không được bỏ trống", HttpStatus.BAD_REQUEST),
    MODULE_BLANK(1203, "MODULE không được bỏ trống", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1204, "Quyền hạn đã tồn tại", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(1205, "Quyền hạn không tồn tại", HttpStatus.NOT_FOUND),



    FILE_EMPTY(2001, "Vui lòng chọn file để tải lên", HttpStatus.BAD_REQUEST),
    FILE_INVALID_TYPE(2002, "Định dạng file không được hỗ trợ. Chỉ chấp nhận các định dạng: JPG, PNG, JPEG", HttpStatus.BAD_REQUEST),


    MOVIE_NAME_BLANK(3000, "Tên phim không được bỏ trống", HttpStatus.BAD_REQUEST),
    DIRECTOR_NAME_BLANK(3001, "Tên đạo diễn không được bỏ trống", HttpStatus.BAD_REQUEST),
    POSTER_BLANK(3003, "Poster không được bỏ trống", HttpStatus.BAD_REQUEST),
    TRAILER_URL_BLANK(3004, "TrailerUrl không được bỏ trống ", HttpStatus.BAD_REQUEST),
    GENRE_BLANK(3005, "Thể loại không được bỏ trống ", HttpStatus.BAD_REQUEST),
    RELEASE_DATE_INVALID(3006, "Ngày khởi chiếu không hợp lệ", HttpStatus.BAD_REQUEST),
    MOVIE_NAME_EXISTED(3007, "Tên phim này đã tồn tại", HttpStatus.BAD_REQUEST),
    MOVIE_NOT_EXISTED(3008, "Phim không tồn tại", HttpStatus.NOT_FOUND),


    ACTOR_NAME_BLANK(3100, "Tên diễn viên không được bỏ trống", HttpStatus.BAD_REQUEST),
    ACTOR_NOT_EXISTED(3101, "Diễn viên không tồn tại", HttpStatus.NOT_FOUND),

    GENRE_NAME_BLANK(3200, "Tên thể loại không được bỏ trống", HttpStatus.BAD_REQUEST),
    GENRE_NOT_EXISTED(3201, "Thể loại không tồn tại", HttpStatus.NOT_FOUND),
    GENRE_EXISTED(3202, "Thể loại này đã tồn tại", HttpStatus.BAD_REQUEST),

    ROOM_NOT_FREE(4000,"Phòng chiếu đã được sử dụng vào thời gian bạn chọn", HttpStatus.BAD_REQUEST),
    ROOM_EXISTED(4001, "Phòng chiếu đã tồn tại", HttpStatus.BAD_REQUEST),

    DATA_VALIDATION_ERROR(9000, "Dữ liệu đầu vào không hợp lệ", HttpStatus.BAD_REQUEST),
    INVALID_DATA_TYPE(9001, "Dữ liệu truyền vào không đúng kiểu dữ liệu", HttpStatus.BAD_REQUEST),
    DATA_TOO_LONG(9002, "Dữ liệu nhập vào vượt quá độ dài cho phép", HttpStatus.BAD_REQUEST),

    UNCATEGORIZED_EXCEPTION(9999, "Đã xảy ra lỗi", HttpStatus.INTERNAL_SERVER_ERROR),

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
