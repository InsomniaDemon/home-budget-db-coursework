package homebudget.backend.dtos.requests;

import java.time.LocalDateTime;

public record OperationRequest(Long articleId, Long debit, Long credit, LocalDateTime createDate) {
}
