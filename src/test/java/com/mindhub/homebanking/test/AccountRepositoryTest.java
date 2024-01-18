package com.mindhub.homebanking.test;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    @Test
    // Verificar que addTransaction no acepte transacciones nulas
    public void testAddTransaction() {
        Account account = new Account();
        Transaction transaction = new Transaction(4000, LocalDateTime.now(),"test", TransactionType.DEBIT);
        assertThrows(NullPointerException.class, () -> account.addTransaccion(null));
        account.addTransaccion(transaction);
        assertTrue(account.getTransactions().contains(transaction));
        assertEquals(account, transaction.getAccount());
    }
    @Test
    public void testSaveNewAccount() {
        Account account = new Account("123456789", LocalDate.now(), 1000,1000);
        accountRepository.save(account);
        assertNotNull(account.getId());
        Account savedAccount = accountRepository.findById(account.getId()).orElse(null);
        assertThat(savedAccount, is(notNullValue()));
        assertThat(savedAccount.getNumber(), equalTo(account.getNumber()));
        assertThat(savedAccount.getCreationDate(), equalTo(account.getCreationDate()));
        assertThat(savedAccount.getBalance(), equalTo(account.getBalance()));
    }

}
