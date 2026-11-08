package homebudget.backend.controllers;

import homebudget.backend.dtos.requests.OperationRequest;
import homebudget.backend.dtos.schema.Operation;
import homebudget.backend.services.OperationsService;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('editor', 'owner')")
    public void deleteOperation(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        operationsService.deleteOperation(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('editor', 'owner')")
    public void postOperation(@AuthenticationPrincipal Jwt jwt, @RequestBody OperationRequest operation) {
        operationsService.insertNewOperation(operation);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('editor', 'owner')")
    public void putOperation(@AuthenticationPrincipal Jwt jwt, @RequestBody OperationRequest operation, @PathVariable Long id) {
        operationsService.updateOperation(id, operation);
    }
}
