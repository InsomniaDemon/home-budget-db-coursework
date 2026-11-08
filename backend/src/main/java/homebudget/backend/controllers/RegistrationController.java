package homebudget.backend.controllers;

import homebudget.backend.dtos.requests.RegNewOwnerRequest;
import homebudget.backend.dtos.requests.RegNewUserRequest;
import homebudget.backend.services.AuthorisationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reg")
public class RegistrationController {
    private final AuthorisationService authorisationService;

    public RegistrationController(AuthorisationService authorisationService) {this.authorisationService = authorisationService;}

    @GetMapping("/owner")
    public boolean isOwnerExist() {
        return authorisationService.doesOwnerExist();
    }

    @PostMapping("/owner")
    public String registerOwner(@RequestBody RegNewOwnerRequest regNewOwnerRequest) {
        return authorisationService.registerNewOwner(regNewOwnerRequest);
    }

    @PostMapping("")
    public String registerUser(@RequestBody RegNewUserRequest regNewUserRequest) {
        return authorisationService.registerNewUser(regNewUserRequest);
    }
}
