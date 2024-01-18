package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.util.AssertionErrors.assertFalse;

@SpringBootTest
public class UtilsTests {
    @Test
    public void cardNumberIsCreated(){

        String cardNumber = Utils.generateCardN();

        assertThat(cardNumber,is(not(emptyOrNullString())));

    }
    @Test
    public void testGenerateSecurityCodeInteger() {
        String securityCode = Utils.generateSecurityCode();
        assertTrue(isInteger(securityCode));
    }

    private boolean isInteger(String input) {
        for (char digit : input.toCharArray()) {
            if (!Character.isDigit(digit)) {
                return false;
            }
        }
        return true;
    }
    @Test
    public void testGenerateSecurityCodeLength() {
        String securityCode = Utils.generateSecurityCode();
        assertEquals(3, securityCode.length());
    }
    @Test
    public void testGenerateCardNFormat() {
        String cardNumber = Utils.generateCardN();
        assertTrue(cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}"));
    }
    @Test
    public void testGenerateCardNLength() {
        String cardNumber = Utils.generateCardN();
        assertEquals(19, cardNumber.length());
    }
    @Test
    public void testGenerateVinFormat() {
        String vin = Utils.generate();
        assertTrue(vin.matches("VIN-\\d{8}"));
    }
    @Test
    public void testGenerateUniqueNumbers() {
        Set<String> generatedNumbers = new HashSet<>();

        for (int i = 0; i < 1000; i++) { // Realiza 1000 generaciones para aumentar la probabilidad de detección de duplicados
            String vin = Utils.generate();
            assertFalse("Duplicated VIN: " + vin, generatedNumbers.contains(vin));
            generatedNumbers.add(vin);
        }
    }
}
