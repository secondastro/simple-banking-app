package com.skypro.bankingapp.model;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private String accountNumber;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @ManyToOne(optional = false)
    private User user;

    public Account() {
    }

    public Account(String accountNumber, double balance, Currency currency) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.currency = currency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}
