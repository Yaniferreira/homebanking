package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;

public class AccountDTO {
    private String number;
    private LocalDate creationDate;
    private double balance;
    public AccountDTO(Account account){
       number= account.getNumber();
       creationDate=account.getCreationDate();
       balance=account.getBalance();
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
}
