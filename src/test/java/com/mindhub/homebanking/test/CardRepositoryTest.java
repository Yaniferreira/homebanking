package com.mindhub.homebanking.test;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardRepositoryTest {
    @Autowired
    CardRepository cardRepository;
    @Test
    // Verificar que no se puede guardar una tarjeta sin tipo
    public void testSaveCardWithoutType() {
        Card card = new Card();
        card.setClient(null);
        assertThrows(DataIntegrityViolationException.class, () -> cardRepository.save(card));
    }

    @Test
    // Verificar que no se puede guardar una tarjeta sin cliente
    public void testSaveCardWithoutClient() {
        Card card = new Card();
        card.setType(CardType.CREDIT);
        assertThrows(DataIntegrityViolationException.class, () -> cardRepository.save(card));
    }
}
