package homebudget.backend.services;

import homebudget.backend.dtos.schema.Article;
import homebudget.backend.dtos.requests.ArticleRequest;
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

    public void addNewArticle(ArticleRequest articleRequest) {
        try {
            articlesRepository.insertNewArticle(articleRequest);
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }

    public void updateArticle(Long id, ArticleRequest articleRequest) {
        try {
            articlesRepository.updateArticle(id, articleRequest);
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }

    public void deleteArticle(Long id) {
        try {
            articlesRepository.deleteArticle(id);
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }

    public void deleteArticleWithOperations(Long id) {
        try {
            articlesRepository.deleteArticleWithOperations(id);
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }
}
