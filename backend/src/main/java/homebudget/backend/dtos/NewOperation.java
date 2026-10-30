package homebudget.backend.dtos;

import java.time.LocalDateTime;

public record NewOperation(Long articleId, Long debit, Long credit, LocalDateTime createDate) {
}
