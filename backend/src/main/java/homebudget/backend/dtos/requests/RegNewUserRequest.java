package homebudget.backend.dtos.requests;

public record RegNewUserRequest(String username, String password, String role, String ownersPassword) {
}
