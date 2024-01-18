package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.dto.NewCardDTO;
import com.mindhub.homebanking.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            @RequestBody NewCardDTO newCardDTO, Authentication authentication) {
        CardColor color = newCardDTO.getColor();
        CardType type = newCardDTO.getType();

        ResponseEntity<String> response = cardService.createCard(color, type, authentication);
        return response;
    }

    @PatchMapping("/clients/current/cards")
    public ResponseEntity<String> softDeleteCard(@RequestParam Long id, Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Card cards = cardService.findById(id);
        if (!cards.isActive() || !cards.getClient().getEmail().equals(authentication.getName())) {
            return new ResponseEntity<>("Card isnt active or client is not authenticated", HttpStatus.FORBIDDEN);
        }
        cardService.editCard(cards);
        return new ResponseEntity<>("Card is canceled", HttpStatus.OK);
    }
}