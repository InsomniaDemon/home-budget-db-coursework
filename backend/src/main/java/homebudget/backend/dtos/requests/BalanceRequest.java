package homebudget.backend.dtos.requests;

public record BalanceRequest(Long debit, Long credit, Long amount) {
}
