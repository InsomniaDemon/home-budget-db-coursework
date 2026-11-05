package homebudget.backend.services;

import homebudget.backend.dtos.Operation;
import homebudget.backend.repositories.OperationsRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OperationsService {
    private final OperationsRepository operationsRepository;

    public OperationsService(OperationsRepository operationsRepository) {
        this.operationsRepository = operationsRepository;
    }

    public List<Operation> getOperationsList() {
        try {
            return operationsRepository.getOperationsList();
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }

    public void deleteOperation(Long id) {
        try {
            operationsRepository.deleteOperation(id);
        } catch (SQLException e) {
            throw new RuntimeException("CHANGE");
        }
    }
}
