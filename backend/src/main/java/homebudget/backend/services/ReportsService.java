package homebudget.backend.services;

import homebudget.backend.dtos.schema.ArticlePercentage;
import homebudget.backend.dtos.schema.IncomeExpenseDynamics;
import homebudget.backend.dtos.schema.NetProfitDynamics;
import homebudget.backend.exceptions.UnknownAppException;
import homebudget.backend.repositories.ReportsRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ReportsService {
    private final ReportsRepository reportsRepository;

    public ReportsService(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    public List<ArticlePercentage> getArticlePercentages(List<Long> articleIds) {
        try {
            return reportsRepository.getArticlePercentages(articleIds);
        } catch (SQLException e) {
            throw new UnknownAppException(e);
        }
    }

    public List<IncomeExpenseDynamics> getIncomeExpenseDynamics(String dateFrom, String dateTo, List<Long> articleIds) {
        try {
            return reportsRepository.getIncomeExpenseDynamics(dateFrom, dateTo, articleIds);
        } catch (SQLException e) {
            throw new UnknownAppException(e);
        }
    }

    public List<NetProfitDynamics> getNetProfitDynamics(String dateFrom, String dateTo, List<Long> articleIds) {
        try {
            return reportsRepository.getNetProfitDynamics(dateFrom, dateTo, articleIds);
        } catch (SQLException e) {
            throw new UnknownAppException(e);
        }
    }
}
