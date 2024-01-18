package com.mindhub.homebanking.test;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;
    @Test
    public void testSaveTransaction() {
        Account account = new Account("123456789", LocalDate.now(), 1000.0);
        accountRepository.save(account);
        LocalDateTime transactionDateTime = LocalDateTime.now();
        Transaction transaction = new Transaction(account, transactionDateTime, 500.0, TransactionType.CREDIT, "Deposit transaction");
        account.addTransaccion(transaction);
        transactionRepository.save(transaction);

        Transaction foundTransaction = transactionRepository.findByDate(transactionDateTime);
    }

    @Test
    public void testTransactionAccountRelationship() {
        Account account = new Account("123456789", LocalDate.now(), 1000.0);
        Transaction transaction = new Transaction(account, LocalDateTime.now(), 500.0, TransactionType.DEBIT, "Withdrawal transaction");
        account.addTransaccion(transaction);
        transactionRepository.save(transaction);
        Transaction savedTransaction = transactionRepository.findById(transaction.getId()).orElse(null);
        assertThat(savedTransaction, is(notNullValue()));
        assertThat(savedTransaction.getAccount(), equalTo(account));
        assertThat(account.getTransactions(), contains(savedTransaction));
    }
}
