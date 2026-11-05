package homebudget.backend.services;

import homebudget.backend.dtos.Article;
import homebudget.backend.repositories.ArticlesRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ArticlesService {
    private final ArticlesRepository articlesRepository;

    public ArticlesService(ArticlesRepository articlesRepository) {
        this.articlesRepository = articlesRepository;
    }

    public List<Article> getArticlesList() {
        try {
            return articlesRepository.getArticlesList();
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }
}
