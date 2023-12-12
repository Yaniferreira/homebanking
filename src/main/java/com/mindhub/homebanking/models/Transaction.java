package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Long amount;
    private LocalDate date;
    private String description;
    private TransactionType type;
    @ManyToOne
    private Account account;
    public Transaction() {
    }

    public Transaction(Long amount, LocalDate date, String description, TransactionType type) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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