package homebudget.backend.repositories;

import homebudget.backend.dtos.NewOperation;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class OperationsRepository {
    private final DataSource dataSource;

    public OperationsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Long insertNewOperation(NewOperation newOperation) throws SQLException {
        String sql = """
                INSERT INTO operations(article_id, debit, credit, create_date)
                VALUES (?, ?, ?, ?, NULL)
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, newOperation.articleId());
            preparedStatement.setLong(2, newOperation.debit());
            preparedStatement.setLong(3, newOperation.credit());
            preparedStatement.setObject(4, newOperation.createDate());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getLong(1);
            }
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }
}
