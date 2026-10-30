package homebudget.backend.dtos;

import java.time.LocalDateTime;

public record Operation(Long id, Long articleId, Long debit, Long credit, LocalDateTime createDate, Long balanceId) {
}
