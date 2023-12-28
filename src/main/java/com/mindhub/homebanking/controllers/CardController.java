package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientsRepositories clientsRepositories;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<String> createCard(
            @RequestParam CardColor color,
            @RequestParam CardType type,
            Authentication authentication) {
        Client client = clientsRepositories.findByEmail(authentication.getName());

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

        int securityCode= (int) (Math.random() * 999 + 100);

        String number = generateRandomCardNumber();

        String cardholder = client.getFirstName() + " " + client.getLastName();

        LocalDate creationDate = LocalDate.now();
        LocalDate expirationDate = creationDate.plusYears(5);

        Card card = new Card( type, number,securityCode, creationDate,expirationDate,cardholder,color);
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
