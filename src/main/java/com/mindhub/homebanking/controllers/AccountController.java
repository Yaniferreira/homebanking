package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.ClienDTO;
import com.mindhub.homebanking.dto.TransactionsDTO;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
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
}
