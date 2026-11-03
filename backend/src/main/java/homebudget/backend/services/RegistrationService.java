package homebudget.backend.services;

import homebudget.backend.dtos.requests.AuthRequest;
import homebudget.backend.dtos.requests.RegRequest;
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

    public boolean isOwnerExists() {
        try {
            return !usersRepository.isUsersEmpty();
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }

    //TODO: add exception handling
    public String registerNewOwner(RegRequest regRequest) {
        String passwordHash = passwordEncoder.encode(regRequest.password());
        try {
            usersRepository.insertNewOwner(regRequest.username(), passwordHash);
            return jwtService.generateAccessToken(regRequest.username());
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE: " + regRequest.username() + " " + regRequest.password());
        }
    }

    //TODO: add exception handling
    public String registerNewUser(RegRequest regRequest) {
        validatePassword(regRequest.ownersPassword(), getOwnersPasswordHash());
        String passwordHash = passwordEncoder.encode(regRequest.password());
        try {
            usersRepository.insertNewUser(regRequest.username(), passwordHash, regRequest.role());
            return jwtService.generateAccessToken(regRequest.username());
        } catch (SQLException e) {
            /*if ("23505".equals(e.getSQLState())) {
                throw new UsernameAlreadyExistsException("A user with this username already exists");
            }

            throw new DatabaseException("Failed to create user", e);*/
            throw new RuntimeException("CHANGE");
        }
    }

    private String getOwnersPasswordHash() {
        try {
            return usersRepository.getOwnersPasswordHash();
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }

    public String authenticateUser(AuthRequest authRequest) {
        try {
            String passwordHash = usersRepository.getUsersPasswordHash(authRequest.username());
            validatePassword(authRequest.password(), passwordHash);
            return jwtService.generateAccessToken(authRequest.username());
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE INVALID CREDENTIALS " + authRequest.username() + " " + authRequest.password());
        }
    }

    private void validatePassword(String password, String passwordHash) {
        if (!passwordEncoder.matches(password, passwordHash)) {
            throw new RuntimeException("CHANGE INVALID CREDENTIALS " + password + " " + passwordHash);
        }
    }
}
