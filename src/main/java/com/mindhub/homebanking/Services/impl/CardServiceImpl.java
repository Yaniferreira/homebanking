package com.mindhub.homebanking.Services.impl;

import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ClientService clientService;

    @Override
    public List<CardDTO> getClientCards() {
        return cardRepository.findAll()
                .stream()
                .map(CardDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    public ResponseEntity<String> createCard(CardColor color, CardType type, Authentication authentication) {
        Client client = clientService.getAuthClient(authentication.getName());
        long colorTypeCount = client.getCards().stream()
                .filter(card -> card.getColor() == color && card.getType() == type)
                .count();
        long colorCount = client.getCards().stream()
                .filter(card -> card.getColor() == color)
                .count();
        long typeCount = client.getCards().stream()
                .filter(card -> card.getType() == type)
                .count();
        if (typeCount >= 3) {
            return new ResponseEntity<>("You already have 3 cards of type " + type, HttpStatus.FORBIDDEN);
        }
        if (colorTypeCount >= 1) {
            return new ResponseEntity<>("You already have a card of color " + color + " and type " + type, HttpStatus.FORBIDDEN);
        }
        if (colorCount >= 2) {
            return new ResponseEntity<>("You already have a card of color " + color, HttpStatus.FORBIDDEN);
        }

        if (client.getCards().size() >= 6) {
            return new ResponseEntity<>("You have reached the maximum limit of 6 cards", HttpStatus.FORBIDDEN);
        }
        int securityCode = (int) (Math.random() * 900 + 100);
        String number = generateRandomCardNumber();
        String cardholder = client.getFirstName() + " " + client.getLastName();
        LocalDate creationDate = LocalDate.now();
        LocalDate expirationDate = creationDate.plusYears(5);
        Card card = new Card( type,number,securityCode, creationDate,expirationDate,cardholder,color);

        client.addCard(card);
        cardRepository.save(card);

        return new ResponseEntity<>("Card created for the client", HttpStatus.CREATED);
    }

    private String generateRandomCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int section = (int) (Math.random() * 9000 + 1000);
            cardNumber.append(section).append("-");
        }
        return cardNumber.substring(0, cardNumber.length() - 1);
    }

}
