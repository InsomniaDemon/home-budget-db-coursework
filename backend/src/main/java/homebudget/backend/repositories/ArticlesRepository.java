package homebudget.backend.repositories;

import homebudget.backend.dtos.Article;
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

    public Long insertNewArticle(String name) throws SQLException {
        String sql = """
                INSERT INTO articles(name)
                VALUES (?)
                RETURNING id
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getLong("id");
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public List<Article> getArticles() throws SQLException {
        String sql = """
                SELECT * FROM articles
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return mapResultSetToArticlesList(resultSet);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public void updateArticleName(Long id, String newName) throws SQLException {
        String sql = """
                UPDATE articles
                SET name = ?
                WHERE id = ?
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newName);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
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
            preparedStatement.executeQuery();
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
