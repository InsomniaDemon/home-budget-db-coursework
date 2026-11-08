package homebudget.backend.controllers;

import homebudget.backend.dtos.requests.AuthRequest;
import homebudget.backend.services.AuthorisationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthentificationController {
    private final AuthorisationService authorisationService;

    public AuthentificationController(AuthorisationService authorisationService) {this.authorisationService = authorisationService;}

    @PostMapping("")
    public String signInUser(@RequestBody AuthRequest authRequest) {
        return authorisationService.authenticateUser(authRequest);
    }
}
