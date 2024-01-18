package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.TransactionsDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;
    @GetMapping("/accounts")
    public List<AccountDTO> getAccount(){
        return accountService.getAllAccounts();
    }
    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.getAccountById(id);
    }
    @GetMapping("/accounts/{id}/transaction")
    public List<TransactionsDTO>getTransactions(@PathVariable Long id ){
        return accountService.getTransactionsByAccountId(id);
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> createAccount(Authentication authentication){
        Client client = clientService.getAuthClient(authentication.getName());
        if(client.getAccounts().stream().filter(Account::isActive).toList().size()>=3){
            return new ResponseEntity<>("You reach the maximum limit of 3 accounts per client", HttpStatus.FORBIDDEN);
        }
        String number;
        do {
            number =Utils.generate();
        }while (accountService.existsByNumber(number));

        Account account = new Account(number,LocalDate.now(), 0,0);
        client.addAccount(account);
        accountService.saveAccount(account);

        return new ResponseEntity<>("Client account created", HttpStatus.CREATED);
    }
    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<String> softDeleteCard(@RequestParam Long id, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Account accounts = accountService.findById(id);
        if (!accounts.isActive() || !accounts.getClient().getEmail().equals(authentication.getName())) {
            return new ResponseEntity<>("Account isnt active or client is not authenticated", HttpStatus.FORBIDDEN);
        }
        if (accounts.getBalance()!= 0){
            return new ResponseEntity<>("You cannot delete an account that has a remaining balance", HttpStatus.FORBIDDEN);
        }
        accountService.editAccount(accounts);
        return new ResponseEntity<>("Account is canceled", HttpStatus.OK);
    }
}
