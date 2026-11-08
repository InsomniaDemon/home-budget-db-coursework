package homebudget.backend.services;

import homebudget.backend.dtos.schema.User;
import homebudget.backend.dtos.requests.AuthRequest;
import homebudget.backend.dtos.requests.RegNewOwnerRequest;
import homebudget.backend.dtos.requests.RegNewUserRequest;
import homebudget.backend.exceptions.AppException;
import homebudget.backend.exceptions.InvalidCredentialsException;
import homebudget.backend.exceptions.LoginAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import homebudget.backend.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class AuthorisationService {
    private final UsersRepository usersRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthorisationService(UsersRepository usersRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean doesOwnerExist() {
        try {
            return !usersRepository.isUsersEmpty();
        } catch (SQLException e) {
            throw new AppException("UNKNOWN", HttpStatus.INTERNAL_SERVER_ERROR, "unknown exception", e);
        }
    }

    public String registerNewOwner(RegNewOwnerRequest regNewOwnerRequest) {
        if (doesOwnerExist()) {
            throw new AppException("SECOND_OWNER_FORBIDDEN", HttpStatus.FORBIDDEN, "Unable to create second owner");
        }
        String passwordHash = passwordEncoder.encode(regNewOwnerRequest.password());
        try {
            usersRepository.insertNewOwner(regNewOwnerRequest.username(), passwordHash);
            return jwtService.generateAccessToken(regNewOwnerRequest.username(), "owner");
        } catch (SQLException e) {
            throw new AppException("UNKNOWN", HttpStatus.INTERNAL_SERVER_ERROR, "unknown exception", e);
        }
    }

    public String registerNewUser(RegNewUserRequest regNewUserRequest) {
        if (regNewUserRequest.role().equals("owner")) {
            throw new AppException("SECOND_OWNER_FORBIDDEN", HttpStatus.FORBIDDEN, "Unable to create second owner");
        }
        try {
            validatePassword(regNewUserRequest.ownersPassword(), getOwnersPasswordHash());
            String passwordHash = passwordEncoder.encode(regNewUserRequest.password());
            usersRepository.insertNewUser(regNewUserRequest.username(), passwordHash, regNewUserRequest.role());
            return jwtService.generateAccessToken(regNewUserRequest.username(), regNewUserRequest.role());
        } catch (InvalidCredentialsException e) {
            throw new InvalidCredentialsException("Incorrect owner's password", e);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new LoginAlreadyExistsException("User with specified login already exists", e);
            }
            throw new AppException("UNKNOWN", HttpStatus.INTERNAL_SERVER_ERROR, "unknown exception", e);
        }
    }

    private String getOwnersPasswordHash() {
        try {
            return usersRepository.getOwnersPasswordHash();
        } catch (SQLException e) {
            throw new AppException("UNKNOWN", HttpStatus.INTERNAL_SERVER_ERROR, "unknown exception", e);
        }
    }

    public String authenticateUser(AuthRequest authRequest) {
        try {
            User user = usersRepository.getUserByUsername(authRequest.username());
            validatePassword(authRequest.password(), user.password());
            return jwtService.generateAccessToken(authRequest.username(), user.role());
        } catch (SQLException e) {
            if ("24000".equals(e.getSQLState())) {
                throw new InvalidCredentialsException("Incorrect password or username", e);
            }

            throw new AppException("UNKNOWN", HttpStatus.INTERNAL_SERVER_ERROR, "unknown exception", e);
        }
    }

    private void validatePassword(String password, String passwordHash) {
        if (!passwordEncoder.matches(password, passwordHash)) {
            throw new InvalidCredentialsException("Incorrect password or username");
        }
    }
}
