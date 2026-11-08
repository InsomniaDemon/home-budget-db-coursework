package homebudget.backend.dtos.response;

public record ErrorResponse(
        String error,
        String message,
        int status
) {}
