package homebudget.backend.controllers;

import homebudget.backend.dtos.Article;
import homebudget.backend.dtos.Operation;
import homebudget.backend.services.ArticlesService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticlesController {
    private final ArticlesService ArticlesService;

    public ArticlesController(ArticlesService ArticlesService) {this.ArticlesService = ArticlesService;}

    @GetMapping("")
    public List<Article> getArticlesList(@AuthenticationPrincipal Jwt jwt) {
        return ArticlesService.getArticlesList();
    }
}
