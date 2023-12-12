package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;

public class TransactionsDTO {
    private long id;
    private Long amount;
    private LocalDate date;
    private String description;
    private TransactionType type;
    public TransactionsDTO(Transaction transaction){
        id= transaction.getId();
        amount=transaction.getAmount();
        date=transaction.getDate();
        description= transaction.getDescription();
        type=transaction.getType();
    }

    public long getId() {
        return id;
    }

    public Long getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getType() {
        return type;
    }
}
