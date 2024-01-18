package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
public class Card {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY )
private Long id;
private CardType type;
private String number;
private String securityCode;
private LocalDate creationDate;
private LocalDate expirationDate;
private String cardHolder;
@ManyToOne
private Client client;
private CardColor color;
private boolean active=true;

    public Card() {
    }

    public Card(CardType type, String number, String securityCode, LocalDate creationDate,
                LocalDate expirationDate, String cardHolder, CardColor color,boolean active) {
        this.type = type;
        this.number = number;
        this.securityCode = securityCode;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.cardHolder = cardHolder;
        this.color = color;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
