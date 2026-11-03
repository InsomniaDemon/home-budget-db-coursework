package homebudget.backend.dtos.requests;

public record RegRequest(String username, String password, String role, String ownersPassword) {
}
