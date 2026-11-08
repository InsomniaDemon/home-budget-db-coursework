package homebudget.backend.repositories;

import homebudget.backend.dtos.schema.Article;
import homebudget.backend.dtos.requests.ArticleRequest;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticlesRepository {
    private final DataSource dataSource;

    public ArticlesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertNewArticle(ArticleRequest articleRequest) throws SQLException {
        String sql = """
                INSERT INTO articles(name)
                VALUES (?)
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, articleRequest.name());
            preparedStatement.execute();
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public List<Article> getArticlesList() throws SQLException {
        String sql = """
                SELECT * FROM articles
                ORDER BY id
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return mapResultSetToArticlesList(resultSet);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public void updateArticle(Long id, ArticleRequest articleRequest) throws SQLException {
        String sql = """
                UPDATE articles
                SET name = ?
                WHERE id = ?
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, articleRequest.name());
            preparedStatement.setLong(2, id);
            preparedStatement.execute();
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public void deleteArticle(Long id) throws SQLException {
        String sql = """
                DELETE FROM articles
                WHERE id = ?
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public void deleteArticleWithOperations(Long id) throws SQLException {
        String sql = """
                BEGIN;
                    DELETE FROM operations
                    WHERE article_id = ?;
                
                    DELETE FROM articles
                    WHERE id = ?;
                COMMIT;
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, id);
            preparedStatement.execute();
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    private Article mapRowToArticle(ResultSet resultSet) throws SQLException {
        return new Article(resultSet.getLong("id"), resultSet.getString("name"));
    }

    private List<Article> mapResultSetToArticlesList(ResultSet resultSet) throws SQLException {
        List<Article> articlesList = new ArrayList<>();
        while (resultSet.next()) {
            articlesList.add(mapRowToArticle(resultSet));
        }
        return articlesList;
    }
}
