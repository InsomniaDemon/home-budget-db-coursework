package homebudget.backend.services;

import homebudget.backend.dtos.schema.ArticlePercentage;
import homebudget.backend.dtos.schema.IncomeExpenseDynamics;
import homebudget.backend.dtos.schema.NetProfitDynamics;
import homebudget.backend.exceptions.UnknownAppException;
import homebudget.backend.repositories.ReportsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportsService {
    private final ReportsRepository reportsRepository;

    public ReportsService(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    @Transactional
    public List<ArticlePercentage> getArticlePercentages(List<Long> articleIds) {
        try {
            return reportsRepository.getArticlePercentages(articleIds);
        } catch (SQLException e) {
            throw new UnknownAppException(articleIds.toString() + " " + e.getSQLState(), e);
        }
    }

    @Transactional
    public List<IncomeExpenseDynamics> getIncomeExpenseDynamics(LocalDateTime dateFrom, LocalDateTime dateTo, List<Long> articleIds) {
        try {
            return reportsRepository.getIncomeExpenseDynamics(dateFrom, dateTo, articleIds);
        } catch (SQLException e) {
            throw new UnknownAppException(articleIds.toString() + " " + dateFrom + " " + dateTo + " " + e.getSQLState(), e);
        }
    }

    @Transactional
    public List<NetProfitDynamics> getNetProfitDynamics(LocalDateTime dateFrom, LocalDateTime dateTo, List<Long> articleIds) {
        try {
            return reportsRepository.getNetProfitDynamics(dateFrom, dateTo, articleIds);
        } catch (SQLException e) {
            throw new UnknownAppException(articleIds.toString() + " " + dateFrom + " " + dateTo + " " + e.getSQLState(), e);
        }
    }
}
