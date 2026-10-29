package homebudget.backend.repositories;

import homebudget.backend.dtos.User;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UsersRepository {
    private final DataSource dataSource;

    public UsersRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isUsersEmpty() throws SQLException {
        String sql = """
                SELECT * FROM users
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
                return !resultSet.next();
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public void insertNewOwner(String username, String passwordHash) throws SQLException {
        String sql = """
                INSERT INTO users(username, password, role)
                VALUES (?, ?, 'owner')
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.execute();
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }
}
