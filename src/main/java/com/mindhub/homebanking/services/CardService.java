package com.mindhub.homebanking.services;


import com.mindhub.homebanking.dto.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface CardService {
    List<CardDTO> getClientCards ();
    void editCard(Card card);
    ResponseEntity<String> createCard(CardColor color, CardType type, Authentication authentication);
    Card findById(Long id);
}
