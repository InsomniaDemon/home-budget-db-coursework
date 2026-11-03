package homebudget.backend.services;

import homebudget.backend.dtos.User;
import homebudget.backend.dtos.requests.AuthRequest;
import homebudget.backend.dtos.requests.RegNewOwnerRequest;
import homebudget.backend.dtos.requests.RegNewUserRequest;
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
    public String registerNewOwner(RegNewOwnerRequest regNewOwnerRequest) {
        String passwordHash = passwordEncoder.encode(regNewOwnerRequest.password());
        try {
            usersRepository.insertNewOwner(regNewOwnerRequest.username(), passwordHash);
            return jwtService.generateAccessToken(regNewOwnerRequest.username(), "owner");
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE: " + regNewOwnerRequest.username() + " " + regNewOwnerRequest.password());
        }
    }

    //TODO: add exception handling
    public String registerNewUser(RegNewUserRequest regNewUserRequest) {
        validatePassword(regNewUserRequest.ownersPassword(), getOwnersPasswordHash());
        String passwordHash = passwordEncoder.encode(regNewUserRequest.password());
        try {
            usersRepository.insertNewUser(regNewUserRequest.username(), passwordHash, regNewUserRequest.role());
            return jwtService.generateAccessToken(regNewUserRequest.username(), regNewUserRequest.role());
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
            User user = usersRepository.getUserByUsername(authRequest.username());
            validatePassword(authRequest.password(), user.password());
            return jwtService.generateAccessToken(authRequest.username(), user.role());
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
