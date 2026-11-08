package homebudget.backend.repositories;

import homebudget.backend.dtos.requests.OperationRequest;
import homebudget.backend.dtos.schema.Operation;
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
public class OperationsRepository {
    private final DataSource dataSource;

    public OperationsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertNewOperation(OperationRequest operationRequest) throws SQLException {
        String sql = """
                INSERT INTO operations(article_id, debit, credit, create_date)
                VALUES (?, ?, ?, ?)
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, operationRequest.articleId());
            preparedStatement.setLong(2, operationRequest.debit());
            preparedStatement.setLong(3, operationRequest.credit());
            preparedStatement.setObject(4, operationRequest.createDate());
            preparedStatement.execute();
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public void deleteOperation(Long id) throws SQLException {
        String sql = """
                DELETE FROM operations
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

    public List<Operation> getOperationsList() throws SQLException {
        String sql = """
                SELECT id, article_id, debit, credit,
                       to_char(create_date, 'YYYY-MM-DD HH24:MI') AS create_date,
                       balance_id FROM operations
                ORDER BY id
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return mapRowToOperationsList(resultSet);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public void updateOperation(Long id, OperationRequest operationRequest) throws SQLException {
        String sql = """
                UPDATE operations
                SET article_id = ?,
                    debit = ?,
                    credit = ?,
                    create_date = ?
                WHERE id = ?
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, operationRequest.articleId());
            preparedStatement.setLong(2, operationRequest.debit());
            preparedStatement.setLong(3, operationRequest.credit());
            preparedStatement.setObject(4, operationRequest.createDate());
            preparedStatement.setLong(5, id);
            preparedStatement.execute();
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public Operation mapRowToOperation(ResultSet resultSet) throws SQLException {
        return new Operation(
                resultSet.getLong("id"),
                resultSet.getObject("article_id", Long.class),
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
