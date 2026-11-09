package homebudget.backend.exceptions;

import org.springframework.http.HttpStatus;

public class UnknownAppException extends AppException {
    public UnknownAppException() {
        super("UNKNOWN_EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR, "unknown");
    }

    public UnknownAppException(String message, Throwable cause) {
        super("UNKNOWN_EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }

    public UnknownAppException(Throwable cause) {
        super("UNKNOWN_EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR, "unknown", cause);
    }
}
