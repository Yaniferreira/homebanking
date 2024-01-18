package com.mindhub.homebanking.utils;

import java.util.Random;

public class Utils {
    public static String generateSecurityCode() {
        Random random = new Random();
        int cvv = 100 + random.nextInt(900);
        String formattedCvv = String.format("%03d", cvv);
        return formattedCvv;
    }
    public static String generateCardN(){
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int section = 1000 + random.nextInt(9000);
            cardNumber.append(section);
            if (i < 3) {
                cardNumber.append("-");
            }
        }
        return cardNumber.toString();
    }
    public static String generate(){
        String number=  "VIN-" + getRandomNumber(10000000, 99999999);
        return number;
    }
    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
