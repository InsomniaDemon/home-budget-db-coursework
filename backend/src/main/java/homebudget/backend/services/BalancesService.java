package homebudget.backend.services;

import homebudget.backend.dtos.schema.Balance;
import homebudget.backend.exceptions.AppException;
import homebudget.backend.exceptions.DeletingIncludedOperationException;
import homebudget.backend.exceptions.UnknownAppException;
import homebudget.backend.repositories.BalancesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            throw new UnknownAppException(e);
        }
    }

    public void insertNewBalance() {
        try {
            balancesRepository.insertNewBalance();
        } catch (SQLException e) {
            if ("HB003".equals(e.getSQLState())) {
                throw new AppException("DEBIT_AND_CREDIT_EQUALS_ZERO", HttpStatus.CONFLICT, "Balance was not created: sum(debit) and sum(credit) equals zero", e);
            }
            throw new UnknownAppException(e);
        }
    }

    public void deleteBalance(Long id) {
        try {
            balancesRepository.deleteBalance(id);
        } catch (SQLException e) {
            throw new UnknownAppException(e);
        }
    }

    @Transactional
    public void deleteBalanceWithOperations(Long id) {
        try {
            balancesRepository.deleteBalanceWithOperations(id);
        } catch (SQLException e) {
            throw new UnknownAppException(e);
        }
    }
}
