package com.mindhub.homebanking.dto;

import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import org.springframework.security.core.Authentication;

public class NewCardDTO {
    private CardColor color;
    private CardType type;

    public CardColor getColor() {
        return color;
    }

    public CardType getType() {
        return type;
    }

}
