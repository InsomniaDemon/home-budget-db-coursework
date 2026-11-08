package homebudget.backend.exceptions;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus httpStatus;

    public AppException(String errorCode, HttpStatus httpStatus, String message) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public AppException(String errorCode, HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() { return errorCode; }
    public HttpStatus getHttpStatus() { return httpStatus; }
}