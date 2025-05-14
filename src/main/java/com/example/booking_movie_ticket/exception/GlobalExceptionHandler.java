package com.example.booking_movie_ticket.exception;

import com.example.booking_movie_ticket.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handleException(Exception exception) {
        log.error("Exception: ", exception);
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptions(MethodArgumentNotValidException exception) {

        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<String> errors = fieldErrors.stream().map(
                        error -> {
                            String enumKey = error.getDefaultMessage();
                            ErrorCode errorCode;
                            try {
                                errorCode = ErrorCode.valueOf(enumKey);
                            } catch (Exception e) {
                                errorCode = ErrorCode.DATA_VALIDATION_ERROR;
                            }
                            return errorCode.getMessage();
                        })
                .collect(Collectors.toList());

        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.DATA_VALIDATION_ERROR.getCode());
        apiResponse.setMessage(ErrorCode.DATA_VALIDATION_ERROR.getMessage());
        apiResponse.setError(errors.size() > 1 ? errors : errors.get(0));
        return ResponseEntity.status(ErrorCode.DATA_VALIDATION_ERROR.getStatusCode()).body(apiResponse);
        //        String enumKey = exception.getFieldError().getDefaultMessage();
//        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
//        ApiResponse apiResponse = new ApiResponse();
//        apiResponse.setCode(errorCode.getCode());
//        apiResponse.setMessage(errorCode.getMessage());
//        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);


        //List<String> errors = exception.getBindingResult().getFieldErrors().stream()
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.error("Exception: ", ex);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.INVALID_DATA_TYPE.getCode());
        apiResponse.setMessage(ErrorCode.INVALID_DATA_TYPE.getMessage());

        return ResponseEntity.status(ErrorCode.INVALID_DATA_TYPE.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Exception: ", ex);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.DATA_VALIDATION_ERROR.getCode());
        apiResponse.setMessage(ErrorCode.DATA_VALIDATION_ERROR.getMessage());
        apiResponse.setError(ex.getMessage());
        return ResponseEntity.status(ErrorCode.DATA_VALIDATION_ERROR.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.error("Exception: ", ex);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(404);
        apiResponse.setMessage("Không tìm thấy tài nguyên yêu cầu.");
        apiResponse.setError(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(value = {
            UsernameNotFoundException.class,
            BadCredentialsException.class
    })
    public ResponseEntity<ApiResponse> handleBadCredentialsException(Exception exception) {
        log.error("Exception: ", exception);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.INVALID_LOGIN_CREDENTIALS_ERROR.getCode());
        apiResponse.setMessage(ErrorCode.INVALID_LOGIN_CREDENTIALS_ERROR.getMessage());

        return ResponseEntity.status(ErrorCode.INVALID_LOGIN_CREDENTIALS_ERROR.getStatusCode()).body(apiResponse);
    }

    // Xử lý lỗi thiếu cookie
    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<ApiResponse> handleMissingCookieException(MissingRequestCookieException ex) {
        log.error("Exception: ", ex);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.REFRESH_TOKEN_NOT_PRESENT_IN_COOKIES.getCode());
        apiResponse.setMessage(ErrorCode.REFRESH_TOKEN_NOT_PRESENT_IN_COOKIES.getMessage());

        return ResponseEntity.status(ErrorCode.REFRESH_TOKEN_NOT_PRESENT_IN_COOKIES.getStatusCode()).body(apiResponse);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }



}
