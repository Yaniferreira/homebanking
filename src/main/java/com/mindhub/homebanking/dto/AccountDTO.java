package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDTO {
    private Long id;
    private String number;
    private LocalDate creationDate;
    private double balance;
    private double totalBalance;
    private boolean active=true;
    private List<TransactionsDTO>transactions;
    public AccountDTO(Account account){
        id= account.getId();
       number= account.getNumber();
       creationDate=account.getCreationDate();
       balance=account.getBalance();
       totalBalance=account.getTotalBalance();
       active= account.isActive();
       transactions=account.getTransactions()
               .stream()
               .map(transaction -> new TransactionsDTO(transaction))
               .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public List<TransactionsDTO> getTransactions() {
        return transactions;
    }
}
