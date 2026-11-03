package homebudget.backend.controllers;

import homebudget.backend.dtos.Operation;
import homebudget.backend.services.OperationsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/op")
public class OperationsController {
    private final OperationsService operationsService;

    public OperationsController(OperationsService operationsService) {this.operationsService = operationsService;}

    @GetMapping("")
    public List<Operation> getOperationsList(@AuthenticationPrincipal Jwt jwt) {
        return operationsService.getOperationsList();
    }
}
