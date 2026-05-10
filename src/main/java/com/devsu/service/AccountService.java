package com.devsu.service;

import com.devsu.model.Account;
import com.devsu.model.Client;
import com.devsu.repository.AccountRepository;
import com.devsu.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountService(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> getById(Long id) {
        return accountRepository.findById(id);
    }

    public Account create(Account account, Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with id: " + clientId));
        account.setClient(client);
        return accountRepository.save(account);
    }

    public Optional<Account> update(Long id, Account updatedAccount, Long clientId) {
        return accountRepository.findById(id).map(existing -> {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found with id: " + clientId));
            existing.setAccountNumber(updatedAccount.getAccountNumber());
            existing.setAccountType(updatedAccount.getAccountType());
            existing.setInitialBalance(updatedAccount.getInitialBalance());
            existing.setStatus(updatedAccount.getStatus());
            existing.setClient(client);
            return accountRepository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
