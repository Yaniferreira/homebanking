package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClienDTO {
    private String firstName,lastName,email;
    private List<AccountDTO>accounts;
    public ClienDTO(Client client){
        firstName=client.getFirstName();
        lastName= client.getLastName();
        email= client.getEmail();
        accounts=client.getAccounts()
                .stream()
                .map(account ->new AccountDTO(account)).
                collect(Collectors.toList());
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
}
