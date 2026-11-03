package homebudget.backend.controllers;

import homebudget.backend.dtos.requests.RegNewOwnerRequest;
import homebudget.backend.dtos.requests.RegNewUserRequest;
import homebudget.backend.services.RegistrationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reg")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {this.registrationService = registrationService;}

    @GetMapping("/owner")
    public boolean isOwnerExist() {
        return registrationService.isOwnerExists();
    }

    @PostMapping("/owner")
    public String registerOwner(@RequestBody RegNewOwnerRequest regNewOwnerRequest) {
        return registrationService.registerNewOwner(regNewOwnerRequest);
    }

    @PostMapping("")
    public String registerUser(@RequestBody RegNewUserRequest regNewUserRequest) {
        return registrationService.registerNewUser(regNewUserRequest);
    }
}
