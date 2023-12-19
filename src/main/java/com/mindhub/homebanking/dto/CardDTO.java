package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

import java.time.LocalDate;

public class CardDTO {
    private Long id;
    private CardType type;
    private String number;
    private String securityCode;
    private LocalDate creationDate;
    private LocalDate expirationDate;
    private CardColor color;
   private String cardHolder;
    public CardDTO(Card card){
        id= card.getId();
        type=card.getType();
        number= card.getNumber();
        securityCode= card.getSecurityCode();
        creationDate=card.getCreationDate();
        expirationDate=card.getExpirationDate();
        color=card.getColor();
        cardHolder=card.getCardHolder();
    }

    public Long getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public String getNumber() {
        return number;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public CardColor getColor() {
        return color;
    }

    public String getCardHolder() {
        return cardHolder;
    }
}
