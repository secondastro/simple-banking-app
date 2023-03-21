package com.skypro.bankingapp.service;

import com.skypro.bankingapp.dto.AccountDTO;
import com.skypro.bankingapp.exception.AccountNotFoundException;
import com.skypro.bankingapp.exception.InsufficientFundsException;
import com.skypro.bankingapp.exception.InvalidChangeAmountException;
import com.skypro.bankingapp.model.Account;
import com.skypro.bankingapp.model.Currency;
import com.skypro.bankingapp.model.User;
import com.skypro.bankingapp.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public double getBalance(String username, String accountNumber) {
        Account account = accountRepository.findById(accountNumber).orElseThrow(AccountNotFoundException::new);
        checkUser(account, username);
        return account.getBalance();
    }

    public void changeBalance(String username, String accountNumber, Operation operation, double amount) {
        if (amount <= 0) {
            throw new InvalidChangeAmountException();
        }
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(AccountNotFoundException::new);
        checkUser(account, username);
        if (operation.equals(Operation.DEPOSIT)) {
            depositOnAccount(account, amount);
        } else {
            withdrawFromAccount(account, amount);
        }
        accountRepository.flush();
    }

    public AccountDTO getAccount(String username, String accountNumber) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(AccountNotFoundException::new);
        checkUser(account,username);
        return AccountDTO.fromAccount(account);
    }

    private void withdrawFromAccount(Account account, double amount) {
        if (account.getBalance() < amount) {
            throw new InsufficientFundsException();
        }
        account.setBalance(account.getBalance() - amount);
    }

    private void checkUser(Account account, String username) {
        if (!account.getUser().getUsername().equals(username)) {
            throw new AccountNotFoundException();
        }
    }

    private void depositOnAccount(Account account, double amount) {
        account.setBalance(account.getBalance() + amount);
    }

    public Account createAccount(User user, Currency currency) {
        Account account = new Account(UUID.randomUUID().toString(), 0.0, currency);
        account.setUser(user);
        accountRepository.save(account);
        return account;
    }
}
