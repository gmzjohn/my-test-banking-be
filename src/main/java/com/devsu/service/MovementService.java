package com.devsu.service;

import com.devsu.model.Account;
import com.devsu.model.Movement;
import com.devsu.repository.AccountRepository;
import com.devsu.repository.MovementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MovementService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;

    public MovementService(MovementRepository movementRepository, AccountRepository accountRepository) {
        this.movementRepository = movementRepository;
        this.accountRepository = accountRepository;
    }

    public List<Movement> getAll() {
        return movementRepository.findAll();
    }

    public Optional<Movement> getById(Long id) {
        return movementRepository.findById(id);
    }

    public Movement create(Movement movement, Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with id: " + accountId));
        movement.setAccount(account);
        return movementRepository.save(movement);
    }

    public Optional<Movement> update(Long id, Movement updatedMovement, Long accountId) {
        return movementRepository.findById(id).map(existing -> {
            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found with id: " + accountId));
            existing.setDate(updatedMovement.getDate());
            existing.setMovementType(updatedMovement.getMovementType());
            existing.setAmount(updatedMovement.getAmount());
            existing.setBalance(updatedMovement.getBalance());
            existing.setAccount(account);
            return movementRepository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (movementRepository.existsById(id)) {
            movementRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
