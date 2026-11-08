package homebudget.backend.services;

import homebudget.backend.dtos.schema.Article;
import homebudget.backend.dtos.requests.ArticleRequest;
import homebudget.backend.exceptions.AppException;
import homebudget.backend.exceptions.DeletingIncludedOperationException;
import homebudget.backend.exceptions.UnknownAppException;
import homebudget.backend.repositories.ArticlesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new UnknownAppException(e);
        }
    }

    public void addNewArticle(ArticleRequest articleRequest) {
        try {
            articlesRepository.insertNewArticle(articleRequest);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new AppException("ARTICLE_ALREADY_EXIST", HttpStatus.CONFLICT, "Article with specified name already exist", e);
            }
            throw new UnknownAppException(e);
        }
    }

    public void updateArticle(Long id, ArticleRequest articleRequest) {
        try {
            articlesRepository.updateArticle(id, articleRequest);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new AppException("ARTICLE_ALREADY_EXIST", HttpStatus.CONFLICT, "Article with specified name already exist", e);
            }
            throw new UnknownAppException(e);
        }
    }

    public void deleteArticle(Long id) {
        try {
            articlesRepository.deleteArticle(id);
        } catch (SQLException e) {
            throw new UnknownAppException(e);
        }
    }

    @Transactional
    public void deleteArticleWithOperations(Long id) {
        try {
            articlesRepository.deleteArticleWithOperations(id);
        } catch (SQLException e) {
            if ("HB002".equals(e.getSQLState())) {
                throw new DeletingIncludedOperationException("Article was not deleted: some of its operations are already included in a balance", e);
            }
            throw new UnknownAppException(e);
        }
    }
}
