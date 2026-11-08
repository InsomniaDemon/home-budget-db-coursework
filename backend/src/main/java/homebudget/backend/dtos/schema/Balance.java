package homebudget.backend.dtos.schema;

public record Balance(Long id, String createDate, Long debit, Long credit, Long amount) {
}
