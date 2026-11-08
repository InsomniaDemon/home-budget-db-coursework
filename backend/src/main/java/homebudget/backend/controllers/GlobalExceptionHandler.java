package homebudget.backend.controllers;

import homebudget.backend.dtos.response.ErrorResponse;
import homebudget.backend.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorResponse(ex.getErrorCode(), ex.getMessage(), ex.getHttpStatus().value()));
    }
}