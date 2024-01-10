package com.mindhub.homebanking.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double amount;
    private LocalDateTime date;
    private String description;
    private TransactionType type;
    @ManyToOne
    private Account account;

    public Transaction() {
    }

    public Transaction(double amount, LocalDateTime date, String description, TransactionType type) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    public Transaction(Account account, LocalDateTime now, double amount, TransactionType transactionType, String s) {
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
