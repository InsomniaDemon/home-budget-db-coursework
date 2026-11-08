package homebudget.backend.services;

import homebudget.backend.dtos.schema.Balance;
import homebudget.backend.repositories.BalancesRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class BalancesService {
    private final BalancesRepository balancesRepository;

    public BalancesService(BalancesRepository balancesRepository) {
        this.balancesRepository = balancesRepository;
    }

    public List<Balance> getBalancesList() {
        try {
            return balancesRepository.getBalancesList();
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }

    public void insertNewOperation() {
        try {
            balancesRepository.insertNewBalance();
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }

    public void deleteBalance(Long id) {
        try {
            balancesRepository.deleteBalance(id);
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }

    public void deleteBalanceWithOperations(Long id) {
        try {
            balancesRepository.deleteBalanceWithOperations(id);
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }
}
