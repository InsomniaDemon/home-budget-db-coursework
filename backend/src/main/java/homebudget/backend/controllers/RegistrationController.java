package homebudget.backend.controllers;

import homebudget.backend.services.RegistrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reg")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {this.registrationService = registrationService;}

    @GetMapping("/owner")
    public boolean isOwnerExist() {
        return registrationService.isOwnerExists();
    }
}
