package com.devsu.service;

import com.devsu.model.Account;
import com.devsu.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> getById(Long id) {
        return accountRepository.findById(id);
    }

    public Account create(Account account) {
        return accountRepository.save(account);
    }

    public Optional<Account> update(Long id, Account updatedAccount) {
        return accountRepository.findById(id).map(existing -> {
            existing.setAccountNumber(updatedAccount.getAccountNumber());
            existing.setAccountType(updatedAccount.getAccountType());
            existing.setInitialBalance(updatedAccount.getInitialBalance());
            existing.setStatus(updatedAccount.getStatus());
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
