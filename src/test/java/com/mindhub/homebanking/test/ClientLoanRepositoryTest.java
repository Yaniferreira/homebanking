package com.mindhub.homebanking.test;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientLoanRepositoryTest {
    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Test
    // Guardar un ClientLoan en el repositorio
    public void testSaveClientLoan() {
        ClientLoan clientLoan = new ClientLoan(1000.0, 12);
        clientLoanRepository.save(clientLoan);
        assertNotNull(clientLoan.getId());
    }

    @Test
    // Recuperar un ClientLoan por el cliente asociado
    public void testFindClientLoanByClient() {
        ClientLoan clientLoan = new ClientLoan(2000.0, 24);
        clientLoanRepository.save(clientLoan);
        List<ClientLoan> retrievedClientLoans = clientLoanRepository.findByClient(clientLoan.getClient());

        assertThat(retrievedClientLoans, is(notNullValue()));
        assertThat(retrievedClientLoans, hasSize(greaterThan(0)));
        for (ClientLoan retrievedClientLoan : retrievedClientLoans) {
            assertEquals(clientLoan.getId(), retrievedClientLoan.getId());
            assertEquals(clientLoan.getAmount(), retrievedClientLoan.getAmount());
            assertEquals(clientLoan.getPayments(), retrievedClientLoan.getPayments());
        }
    }
}
