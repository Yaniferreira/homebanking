package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.ClienDTO;
import com.mindhub.homebanking.dto.TransactionsDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientsRepositories;
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
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientsRepositories clientsRepositories;
    @RequestMapping("/accounts")
    public List<AccountDTO> getAccount(){
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }
    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return new AccountDTO (accountRepository.findById(id).orElse(null));
    }
    @RequestMapping("/accounts/{id}/transaction")
    public List<TransactionsDTO>getTransactions(@PathVariable Long id ){
        return accountRepository.findById(id).map(account ->account.getTransactions().stream()
                .map(transactions -> new TransactionsDTO(transactions))
                .collect(Collectors.toList())).orElse(null);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> createAccount(Authentication authentication){
        Client client = clientsRepositories.findByEmail(authentication.getName());
        if(client.getAccounts().size()>=3){
            return new ResponseEntity<>("You reach the maximum limit of 3 accounts per client", HttpStatus.FORBIDDEN);
        }
        String number;
        do {
            number = "VIN" + getAccountNumber(00000000,99999999);
        }while (accountRepository.existsByNumber(number));

        Account account = new Account(number, LocalDate.now(), 0);
        client.addAccount(account);
        accountRepository.save(account);

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
        }while (accountRepository.existsByNumber(number));

        Account account = new Account(number, LocalDate.now(), 0);
        client.addAccount(account);
        accountRepository.save(account);

        return new ResponseEntity<>("Client account created", HttpStatus.CREATED);
    }
    public int getAccountNumber(int min, int max){
        return (int) ((Math.random())*(max-min)+min);
    }
}
