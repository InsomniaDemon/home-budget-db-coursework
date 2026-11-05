package homebudget.backend.controllers;

import homebudget.backend.dtos.Article;
import homebudget.backend.services.ArticlesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticlesController {
    private final ArticlesService articlesService;

    public ArticlesController(ArticlesService ArticlesService) {this.articlesService = ArticlesService;}

    @GetMapping("")
    public List<Article> getArticlesList(@AuthenticationPrincipal Jwt jwt) {
        return articlesService.getArticlesList();
    }

    @PostMapping("")
    @PreAuthorize("hasAnyRole('editor', 'owner')")
    public void postArticle(@AuthenticationPrincipal Jwt jwt, @RequestBody String name) {
        articlesService.addNewArticle(name);
    }

    @DeleteMapping("")
    @PreAuthorize("hasAnyRole('editor', 'owner')")
    public void deleteArticle(@AuthenticationPrincipal Jwt jwt, @RequestBody Long id) {
        articlesService.deleteArticle(id);
    }

    @DeleteMapping("/ops")
    @PreAuthorize("hasAnyRole('editor', 'owner')")
    public void deleteArticleWithOperations(@AuthenticationPrincipal Jwt jwt, @RequestBody Long id) {
        articlesService.deleteArticleWithOperations(id);
    }
}
