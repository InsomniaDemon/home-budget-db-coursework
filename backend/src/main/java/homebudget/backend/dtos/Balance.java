package homebudget.backend.dtos;

public record Balance(Long id, String createDate, Long debit, Long credit, Long amount) {
}
