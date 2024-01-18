package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.TransactionsDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long id);
    Account findById(Long id);
    List<TransactionsDTO> getTransactionsByAccountId(Long accountId);

    boolean existsByNumber(String number);

    void saveAccount(Account account);

    Account findByNumber(String number);

    void editAccount(Account account);
    Account findByClientAndId(Client client, Long id);
}
