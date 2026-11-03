package homebudget.backend.controllers;

import homebudget.backend.dtos.requests.AuthRequest;
import homebudget.backend.services.RegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthentificationController {
    private final RegistrationService registrationService;

    public AuthentificationController(RegistrationService registrationService) {this.registrationService = registrationService;}

    @PostMapping("")
    public String signInUser(@RequestBody AuthRequest authRequest) {
        return registrationService.authenticateUser(authRequest);
    }
}
