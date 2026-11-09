package homebudget.backend.controllers;

import homebudget.backend.dtos.schema.ArticlePercentage;
import homebudget.backend.dtos.schema.IncomeExpenseDynamics;
import homebudget.backend.dtos.schema.NetProfitDynamics;
import homebudget.backend.services.ReportsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ReportsController {
    private final ReportsService reportsService;

    public ReportsController(ReportsService reportsService) {this.reportsService = reportsService;}

    @GetMapping("/percentage")
    public List<ArticlePercentage> getArticlePercentages(@AuthenticationPrincipal Jwt jwt, @RequestParam List<Long> articleIds) {
        return reportsService.getArticlePercentages(articleIds);
    }

    @GetMapping("/i-e-dynamics")
    public List<IncomeExpenseDynamics> getIncomeExpenseDynamics(@AuthenticationPrincipal Jwt jwt, @RequestParam LocalDateTime dateFrom, @RequestParam LocalDateTime dateTo, @RequestParam List<Long> articleIds) {
        return reportsService.getIncomeExpenseDynamics(dateFrom, dateTo, articleIds);
    }

    @GetMapping("/profit")
    public List<NetProfitDynamics> getNetProfitDynamics(@AuthenticationPrincipal Jwt jwt, @RequestParam LocalDateTime dateFrom, @RequestParam LocalDateTime dateTo, @RequestParam List<Long> articleIds) {
        return reportsService.getNetProfitDynamics(dateFrom, dateTo, articleIds);
    }
}
