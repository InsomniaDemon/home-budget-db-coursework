package homebudget.backend.services;

import homebudget.backend.dtos.Balance;
import homebudget.backend.repositories.BalancesRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BalancesService {
    private final BalancesRepository BalancesRepository;

    public BalancesService(BalancesRepository BalancesRepository) {
        this.BalancesRepository = BalancesRepository;
    }

    public List<Balance> getBalancesList() {
        try {
            return BalancesRepository.getBalancesList();
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }
}
