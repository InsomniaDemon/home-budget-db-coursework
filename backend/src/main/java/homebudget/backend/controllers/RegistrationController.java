package homebudget.backend.controllers;

import homebudget.backend.dtos.requests.RegRequest;
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
    public String registerOwner(@RequestBody RegRequest regRequest) {
        return registrationService.registerNewOwner(regRequest);
    }

    @PostMapping("")
    public String registerUser(@RequestBody RegRequest regRequest) {
        return registrationService.registerNewUser(regRequest);
    }
}
