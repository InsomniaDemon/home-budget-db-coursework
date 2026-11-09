package homebudget.backend.services;

import homebudget.backend.dtos.requests.OperationRequest;
import homebudget.backend.dtos.schema.Operation;
import homebudget.backend.exceptions.AppException;
import homebudget.backend.exceptions.DeletingIncludedOperationException;
import homebudget.backend.exceptions.LoginAlreadyExistsException;
import homebudget.backend.exceptions.UnknownAppException;
import homebudget.backend.repositories.OperationsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class OperationsService {
    private final OperationsRepository operationsRepository;

    public OperationsService(OperationsRepository operationsRepository) {
        this.operationsRepository = operationsRepository;
    }

    public void insertNewOperation(OperationRequest operation) {
        try {
            operationsRepository.insertNewOperation(operation);
        } catch (SQLException e) {
            if ("23503".equals(e.getSQLState())) {
                throw new AppException("INVALID_ARTICLE_ID", HttpStatus.CONFLICT, "Invalid article id");
            }
            throw new UnknownAppException(e);
        }
    }

    public List<Operation> getOperationsList() {
        try {
            return operationsRepository.getOperationsList();
        } catch (SQLException e) {
            throw new UnknownAppException(e);
        }
    }

    public void deleteOperation(Long id) {
        try {
            operationsRepository.deleteOperation(id);
        } catch (SQLException e) {
            if ("HB002".equals(e.getSQLState())) {
                throw new DeletingIncludedOperationException("Unable to delete operation that is included into balance", e);
            }
            throw new UnknownAppException(e);
        }
    }

    public void updateOperation(Long id, OperationRequest operation) {
        try {
            operationsRepository.updateOperation(id, operation);
        } catch (SQLException e) {
            if ("HB001".equals(e.getSQLState())) {
                throw new AppException("UPDATING_INCLUDED_OPERATION", HttpStatus.CONFLICT, "Unable to update debit or credit of operation that is included into balance", e);
            } else if ("23503".equals(e.getSQLState())) {
                throw new AppException("INVALID_ARTICLE_ID", HttpStatus.CONFLICT, "Invalid article id");
            }
            throw new UnknownAppException(e);
        }
    }
}
