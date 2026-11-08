package homebudget.backend.repositories;

import homebudget.backend.dtos.schema.Balance;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BalancesRepository {
    private final DataSource dataSource;

    public BalancesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Transactional
    public void insertNewBalance() throws SQLException {
        String sql = """
                CALL insert_new_balance();
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public void deleteBalance(Long id) throws SQLException {
        String sql = """
                DELETE FROM balance
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

    public void deleteBalanceWithOperations(Long id) throws SQLException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
            List<Long> opIds = new ArrayList<>();
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT id FROM operations WHERE balance_id = ?")) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) opIds.add(rs.getLong(1));
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM balance WHERE id = ?")) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM operations WHERE id = ANY(?)")) {
                ps.setArray(1, connection.createArrayOf("bigint", opIds.toArray()));
                ps.executeUpdate();
            }
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public List<Balance> getBalancesList() throws SQLException {
        String sql = """
                SELECT id, to_char(create_date, 'YYYY-MM-DD HH24:MI') AS create_date,
                       debit, credit, amount
                FROM balance
                ORDER BY id
                """;
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return mapRowToBalancesList(resultSet);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public Balance mapRowToBalance(ResultSet resultSet) throws SQLException {
        return new Balance(
                resultSet.getLong("id"),
                resultSet.getString("create_date"),
                resultSet.getLong("debit"),
                resultSet.getLong("credit"),
                resultSet.getLong("amount")
        );
    }

    public List<Balance> mapRowToBalancesList(ResultSet resultSet) throws SQLException {
        List<Balance> Balances = new ArrayList<>();
        while (resultSet.next()) {
            Balances.add(mapRowToBalance(resultSet));
        }
        return Balances;
    }
}
