package homebudget.backend.exceptions;

import org.springframework.http.HttpStatus;

public class DeletingIncludedOperationException extends AppException {
    public DeletingIncludedOperationException(String message) {
        super("DELETING_INCLUDED_OPERATION", HttpStatus.CONFLICT, message);
    }

    public DeletingIncludedOperationException(String message, Throwable cause) {
        super("DELETING_INCLUDED_OPERATION", HttpStatus.CONFLICT, message, cause);
    }
}