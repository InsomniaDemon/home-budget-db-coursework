
package homebudget.backend.repositories;

import homebudget.backend.dtos.schema.ArticlePercentage;
import homebudget.backend.dtos.schema.IncomeExpenseDynamics;
import homebudget.backend.dtos.schema.NetProfitDynamics;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportsRepository {
    private final DataSource dataSource;

    public ReportsRepository(DataSource dataSource) {this.dataSource = dataSource;}

    public List<ArticlePercentage> getArticlePercentages(List<Long> articleIds) throws SQLException {
        String sql = """
                CALL get_article_percentages(?, ?::refcursor)
                """;

        Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setArray(1, connection.createArrayOf("bigint", articleIds.toArray()));
                preparedStatement.setString(2, "percentage_cur");
                preparedStatement.execute();
            }

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("FETCH ALL FROM percentage_cur");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                return mapResultSetToArticlePercentagesList(resultSet);
            }
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public List<IncomeExpenseDynamics> getIncomeExpenseDynamics(LocalDateTime dateFrom, LocalDateTime dateTo, List<Long> articleIds) throws SQLException {

        String sql = """
                CALL get_income_expense_dynamics(?, ?, ?, ?::refcursor)
                """;

        Connection connection = DataSourceUtils.getConnection(dataSource);

        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setObject(1, dateFrom);
                preparedStatement.setObject(2, dateTo);
                preparedStatement.setArray(3, connection.createArrayOf("bigint", articleIds.toArray()));
                preparedStatement.setString(4, "income_expense_cur");
                preparedStatement.execute();
            }

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("FETCH ALL FROM income_expense_cur");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                return mapResultSetToIncomeExpenseDynamicsList(resultSet);
            }
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public List<NetProfitDynamics> getNetProfitDynamics(LocalDateTime dateFrom, LocalDateTime dateTo, List<Long> articleIds) throws SQLException {
        String sql = """
                CALL get_net_profit_dynamics(?, ?, ?, ?::refcursor)
                """;

        Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setObject(1, dateFrom);
                preparedStatement.setObject(2, dateTo);
                preparedStatement.setArray(3, connection.createArrayOf("bigint", articleIds.toArray()));
                preparedStatement.setString(4, "profit_cur");
                preparedStatement.execute();
            }

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("FETCH ALL FROM profit_cur");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                return  mapResultSetToNetProfitDynamicsList(resultSet);
            }
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public ArticlePercentage mapRowToArticlePercentage(ResultSet resultSet) throws SQLException {
        return new ArticlePercentage(
                resultSet.getLong("article_id"),
                resultSet.getDouble("debit"),
                resultSet.getDouble("credit"),
                resultSet.getDouble("amount")
        );
    }

    public List<ArticlePercentage> mapResultSetToArticlePercentagesList(ResultSet resultSet) throws SQLException {
        List<ArticlePercentage> percentages = new ArrayList<>();

        while (resultSet.next()) {
            percentages.add(mapRowToArticlePercentage(resultSet));
        }

        return percentages;
    }

    public IncomeExpenseDynamics mapRowToIncomeExpenseDynamics(ResultSet resultSet) throws SQLException {
        return new IncomeExpenseDynamics(
                resultSet.getString("date"),
                resultSet.getLong("debit"),
                resultSet.getLong("credit")
        );
    }

    public List<IncomeExpenseDynamics> mapResultSetToIncomeExpenseDynamicsList(ResultSet resultSet) throws SQLException {
        List<IncomeExpenseDynamics> dynamics = new ArrayList<>();

        while (resultSet.next()) {
            dynamics.add(mapRowToIncomeExpenseDynamics(resultSet));
        }

        return dynamics;
    }

    public NetProfitDynamics mapRowToNetProfitDynamics(ResultSet resultSet) throws SQLException {
        return new NetProfitDynamics(
                resultSet.getString("date"),
                resultSet.getLong("net_profit")
        );
    }

    public List<NetProfitDynamics> mapResultSetToNetProfitDynamicsList(ResultSet resultSet) throws SQLException {
        List<NetProfitDynamics> dynamics = new ArrayList<>();

        while (resultSet.next()) {
            dynamics.add(mapRowToNetProfitDynamics(resultSet));
        }

        return dynamics;
    }
}
