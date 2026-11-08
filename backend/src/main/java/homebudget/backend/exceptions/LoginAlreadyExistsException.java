package homebudget.backend.exceptions;

import org.springframework.http.HttpStatus;

public class LoginAlreadyExistsException extends AppException {
    public LoginAlreadyExistsException(String message) {
        super("LOGIN_ALREADY_EXISTS", HttpStatus.CONFLICT, message);
    }

    public LoginAlreadyExistsException(String message, Throwable cause) {
        super("LOGIN_ALREADY_EXISTS", HttpStatus.UNAUTHORIZED, message, cause);
    }
}