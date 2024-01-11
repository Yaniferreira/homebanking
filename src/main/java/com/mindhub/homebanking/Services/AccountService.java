package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.dto.AccountDTO;
import com.mindhub.homebanking.dto.ClientDTO;
import com.mindhub.homebanking.dto.TransactionsDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long id);
    List<TransactionsDTO> getTransactionsByAccountId(Long accountId);

    boolean existsByNumber(String number);

    void saveAccount(Account account);

    Account findByNumber(String number);
}
