package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.CardService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.dto.NewCardDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private ClientService clientService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<String> createCard(
            @RequestBody NewCardDTO newCardDTO,Authentication authentication) {
        CardColor color = newCardDTO.getColor();
        CardType type = newCardDTO.getType();

        ResponseEntity<String> response = cardService.createCard(color, type, authentication);
        return response;
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
