package com.mindhub.homebanking.dto;

import org.springframework.security.core.Authentication;

public class NewTransactionDTO {
    private Long amount;
    private String descriptions,sourceAccountNumber,targetAccountNumber;
    private Authentication authentication;

    public Long getAmount() {
        return amount;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public Authentication getAuthentication() {
        return authentication;
    }
}
