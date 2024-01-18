package com.mindhub.homebanking.test;

import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoanRepositoryTest {
    @Autowired
    LoanRepository loanRepository;
    @Test
    public void existLoans(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));

    }
    @Test

    public void existPersonalLoan(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

    }
    @Test
    public void saveAndRetrieveLoan() {
        Loan newLoan = new Loan("Test Loan", 100000, List.of(12, 24, 36));
        loanRepository.save(newLoan);

        Loan retrievedLoan = loanRepository.findById(newLoan.getId()).orElse(null);
        assertThat(retrievedLoan, is(notNullValue()));
        assertThat(retrievedLoan.getName(), is("Test Loan"));
    }

    @Test
    public void deleteLoan() {
        Loan newLoan = new Loan("Loan to Delete", 50000, List.of(6, 12, 18));
        loanRepository.save(newLoan);

        loanRepository.deleteById(newLoan.getId());
        Loan deletedLoan = loanRepository.findById(newLoan.getId()).orElse(null);
        assertThat(deletedLoan, is(nullValue()));
    }
}
