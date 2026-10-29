package homebudget.backend.services;

import org.springframework.security.crypto.password.PasswordEncoder;

import homebudget.backend.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class RegistrationService {
    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UsersRepository usersRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    //TODO: add exception handling
    public String registerNewOwner(String username, String password) {
        String passwordHash = passwordEncoder.encode(password);
        try {
            usersRepository.insertNewOwner(username, passwordHash);
            return jwtService.generateAccessToken(username, "owner");
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }

    //TODO: add exception handling
    public String registerNewUser(String username, String password, String role) {
        String passwordHash = passwordEncoder.encode(password);
        try {
            usersRepository.insertNewUser(username, password, role);
            return jwtService.generateAccessToken(username, role);
        } catch (SQLException e) {
            /*if ("23505".equals(e.getSQLState())) {
                throw new UsernameAlreadyExistsException("A user with this username already exists");
            }

            throw new DatabaseException("Failed to create user", e);*/
            throw new RuntimeException("CHANGE");
        }
    }
}
