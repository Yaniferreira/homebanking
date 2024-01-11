package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.RoleType;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName,lastName,email;
    private  RoleType role;
    private List<AccountDTO>accounts;
    private List<ClientLoanDTO>loans;
    private List<CardDTO>cards;
    public ClientDTO(Client client){
        id= client.getId();
        firstName=client.getFirstName();
        lastName= client.getLastName();
        email= client.getEmail();
        role=client.getRole();
        accounts=client.getAccounts()
                .stream()
                .map(account ->new AccountDTO(account)).
                collect(Collectors.toList());
        loans=client.getClientLoans()
                .stream()
                .map(clientLoan ->new ClientLoanDTO(clientLoan)).
                collect(Collectors.toList());
        cards=client.getCards()
                .stream()
                .map(card -> new CardDTO(card))
                .collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public RoleType getRole() {
        return role;
    }
}