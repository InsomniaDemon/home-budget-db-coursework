package homebudget.backend.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends AppException {
    public InvalidCredentialsException(String message) {
        super("INVALID_CREDENTIALS", HttpStatus.UNAUTHORIZED, message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super("INVALID_CREDENTIALS", HttpStatus.UNAUTHORIZED, message, cause);
    }
}
