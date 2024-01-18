package com.mindhub.homebanking.services.impl;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.TransactionsDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientService clientService;

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }
    @Override
    public AccountDTO getAccountById(Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<TransactionsDTO> getTransactionsByAccountId(Long id) {
        return accountRepository.findById(id)
                .map(account -> account.getTransactions().stream()
                        .map(TransactionDTO -> new TransactionsDTO(TransactionDTO))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }
    @Override
    public boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }
    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }
    @Override
    public void editAccount(Account account) {
        Account accounts= accountRepository.findById(account.getId()).orElse(null);
        accounts.setActive(false);
        accountRepository.save(accounts);
    }

    @Override
    public Account findByClientAndId(Client client, Long id) {
        return accountRepository.findByClientAndId(client,id);
    }
}