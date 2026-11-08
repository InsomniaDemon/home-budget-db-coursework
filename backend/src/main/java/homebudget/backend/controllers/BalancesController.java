package homebudget.backend.controllers;

import homebudget.backend.dtos.schema.Balance;
import homebudget.backend.services.BalancesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/balances")
public class BalancesController {
    private final BalancesService balancesService;

    public BalancesController(BalancesService balancesService) {this.balancesService = balancesService;}

    @GetMapping("")
    public List<Balance> getBalancesList(@AuthenticationPrincipal Jwt jwt) {
        return balancesService.getBalancesList();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('editor', 'owner')")
    public void deleteBalance(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        balancesService.deleteBalance(id);
    }

    @DeleteMapping("/ops/{id}")
    @PreAuthorize("hasAnyRole('editor', 'owner')")
    public void deleteBalanceWithOperations(@AuthenticationPrincipal Jwt jwt, @PathVariable Long id) {
        balancesService.deleteBalanceWithOperations(id);
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('editor', 'owner')")
    public void postBalance(@AuthenticationPrincipal Jwt jwt) {
        balancesService.insertNewBalance();
    }
}
