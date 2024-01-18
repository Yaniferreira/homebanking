package com.mindhub.homebanking.test;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.RoleType;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {
    @Autowired
    ClientRepository clientRepository;

    @Test
    // Verificar que se puede eliminar un cliente del repositorio
    public void testDeleteClient() {
        Client client = new Client("FirstName", "LastName", "email@example.com", "password", RoleType.CLIENT);
        clientRepository.save(client);
        clientRepository.delete(client);

        Client deletedClient = clientRepository.findById(client.getId()).orElse(null);
        assertNull(deletedClient);
    }

    @Test
    // Verificar que se puede actualizar la información de un cliente en el repositorio
    public void testUpdateClientInformation() {
        Client originalClient = new Client("FirstName", "LastName", "email@example.com", "password", RoleType.CLIENT);
        clientRepository.save(originalClient);

        originalClient.setFirstName("UpdatedFirstName");
        originalClient.setLastName("UpdatedLastName");
        originalClient.setEmail("updated_email@example.com");
        clientRepository.save(originalClient);

        Client updatedClient = clientRepository.findById(originalClient.getId()).orElse(null);
        assertThat(updatedClient, is(notNullValue()));
        assertThat(updatedClient.getFirstName(), equalTo("UpdatedFirstName"));
        assertThat(updatedClient.getLastName(), equalTo("UpdatedLastName"));
        assertThat(updatedClient.getEmail(), equalTo("updated_email@example.com"));
    }
}
