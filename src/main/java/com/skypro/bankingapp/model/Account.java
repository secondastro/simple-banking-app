package com.skypro.bankingapp.model;

import java.util.Objects;

public class Account {
    private final String accountNumber;
    private double balance;
    private final Currency currency;

    public Account(String accountNumber, double balance, Currency currency) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return getAccountNumber().equals(account.getAccountNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNumber());
    }
}
