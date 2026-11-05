package homebudget.backend.dtos;

public record Balance(Long id, String create_date, Long debit, Long credit, Long amount) {
}
