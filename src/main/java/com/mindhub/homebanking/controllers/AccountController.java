package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.TransactionsDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;
    @RequestMapping("/accounts")
    public List<AccountDTO> getAccount(){
        return accountService.getAllAccounts();
    }
    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.getAccountById(id);
    }
    @RequestMapping("/accounts/{id}/transaction")
    public List<TransactionsDTO>getTransactions(@PathVariable Long id ){
        return accountService.getTransactionsByAccountId(id);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> createAccount(Authentication authentication){
        Client client = clientService.getAuthClient(authentication.getName());
        if(client.getAccounts().size()>=3){
            return new ResponseEntity<>("You reach the maximum limit of 3 accounts per client", HttpStatus.FORBIDDEN);
        }
        String number;
        do {
            number = "VIN" + getAccountNumber(00000000,99999999);
        }while (accountService.existsByNumber(number));

        Account account = new Account(number, LocalDate.now(), 0);
        client.addAccount(account);
        accountService.saveAccount(account);

        return new ResponseEntity<>("Client account created", HttpStatus.CREATED);
    }

    @PostMapping("/clients/current/accounts/first")
    public ResponseEntity<String> createAccountFirst(Client client){
        if(client.getAccounts().size()>=3){
            return new ResponseEntity<>("You reach the maximum limit of 3 accounts per client",HttpStatus.FORBIDDEN);
        }
        String number;
        do {
            number = "VIN" + getAccountNumber(00000000,99999999);
        }while (accountService.existsByNumber(number));

        Account account = new Account(number, LocalDate.now(), 0);
        client.addAccount(account);
        accountService.saveAccount(account);

        return new ResponseEntity<>("Client account created", HttpStatus.CREATED);
    }
    public int getAccountNumber(int min, int max){
        return (int) ((Math.random())*(max-min)+min);
    }
}
