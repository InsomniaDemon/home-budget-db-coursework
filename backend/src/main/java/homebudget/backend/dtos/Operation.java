package homebudget.backend.dtos;

public record Operation(Long id, Long articleId, Long debit, Long credit, String createDate, Long balanceId) {
}
