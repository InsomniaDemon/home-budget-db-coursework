package homebudget.backend.repositories;

import homebudget.backend.dtos.NewOperation;
import homebudget.backend.dtos.Operation;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<Operation> getOperationsList() throws SQLException {
        String sql = """
                SELECT * FROM operations
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return mapRowToOperationsList(resultSet);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public Operation mapRowToOperation(ResultSet resultSet) throws SQLException {
        return new Operation(
                resultSet.getLong("id"),
                resultSet.getLong("article_id"),
                resultSet.getLong("debit"),
                resultSet.getLong("credit"),
                resultSet.getString("create_date"),
                resultSet.getObject("balance_id", Long.class)
        );
    }

    public List<Operation> mapRowToOperationsList(ResultSet resultSet) throws SQLException {
        List<Operation> operations = new ArrayList<>();
        while (resultSet.next()) {
            operations.add(mapRowToOperation(resultSet));
        }
        return operations;
    }
}
