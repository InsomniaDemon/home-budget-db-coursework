package homebudget.backend.controllers;

import homebudget.backend.dtos.Balance;
import homebudget.backend.services.BalancesService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/balances")
public class BalancesController {
    private final BalancesService BalancesService;

    public BalancesController(BalancesService BalancesService) {this.BalancesService = BalancesService;}

    @GetMapping("")
    public List<Balance> getBalancesList(@AuthenticationPrincipal Jwt jwt) {
        return BalancesService.getBalancesList();
    }
}
