
package homebudget.backend.repositories;

import homebudget.backend.dtos.schema.ArticlePercentage;
import homebudget.backend.dtos.schema.IncomeExpenseDynamics;
import homebudget.backend.dtos.schema.NetProfitDynamics;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportsRepository {
    private final DataSource dataSource;

    public ReportsRepository(DataSource dataSource) {this.dataSource = dataSource;}

    public List<ArticlePercentage> getArticlePercentages(List<Long> articleIds) throws SQLException {
        String sql = """
                CALL get_article_percentages(?, ?)
                """;

        Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setArray(1, connection.createArrayOf("bigint", articleIds.toArray()));
                preparedStatement.setString(2, "res_cur");
                preparedStatement.execute();
            }

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("FETCH ALL FROM res_cur");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                return mapResultSetToArticlePercentagesList(resultSet);
            }
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public List<IncomeExpenseDynamics> getIncomeExpenseDynamics(String dateFrom, String dateTo, List<Long> articleIds) throws SQLException {

        String sql = """
                CALL get_income_expense_dynamics(?, ?, ?, ?)
                """;

        Connection connection = DataSourceUtils.getConnection(dataSource);

        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDate(1, Date.valueOf(dateFrom));
                preparedStatement.setDate(2, Date.valueOf(dateTo));
                preparedStatement.setArray(3, connection.createArrayOf("bigint", articleIds.toArray()));
                preparedStatement.setString(4, "res_cur");
                preparedStatement.execute();
            }

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("FETCH ALL FROM res_cur");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                return mapResultSetToIncomeExpenseDynamicsList(resultSet);
            }
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public List<NetProfitDynamics> getNetProfitDynamics(String dateFrom, String dateTo, List<Long> articleIds) throws SQLException {
        String sql = """
                CALL get_net_profit_dynamics(?, ?, ?, ?)
                """;

        Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDate(1, Date.valueOf(dateFrom));
                preparedStatement.setDate(2, Date.valueOf(dateTo));
                preparedStatement.setArray(3, connection.createArrayOf("bigint", articleIds.toArray()));
                preparedStatement.setString(4, "res_cur");
                preparedStatement.execute();
            }

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("FETCH ALL FROM res_cur");
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                return  mapResultSetToNetProfitDynamicsList(resultSet);
            }
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
        }
    }

    public ArticlePercentage mapRowToArticlePercentage(ResultSet resultSet) throws SQLException {
        return new ArticlePercentage(
                resultSet.getObject("article_id", Long.class),
                resultSet.getObject("debit", Double.class),
                resultSet.getObject("credit", Double.class),
                resultSet.getObject("amount", Double.class)
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
                resultSet.getObject("debit", Long.class),
                resultSet.getObject("credit", Long.class)
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
