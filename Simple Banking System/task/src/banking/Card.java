package banking;


import TestJDBC.TestJDBC;

import java.util.*;

public class Card {
    private String cardNumber;
    private String pin;

//    private static Map<String, String> generatedCards =
//            new HashMap<>();
    public final int BIN = 400000;



    public Card() {
        boolean continueGeneration = true;
        while (continueGeneration) {
            String generatedNumber = this.generateNumber();

            if (!TestJDBC.checkCard(generatedNumber)) {
                continueGeneration = false;
                this.cardNumber = generatedNumber;
                this.pin = this.generatePin();

            }
        }
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public String getPin() {
        return this.pin;
    }

    public int getBalance() {
        return TestJDBC.getBalance(this.getCardNumber());
    }

//    public Map<String, String> getGeneratedCards() {
//        return generatedCards;
//    }

    public String generateNumber() {
        Random random = new Random();
        String randomNumber1 = String.format("%09d",
                random.nextInt(1000000000));
        StringBuilder ress = new StringBuilder();
        ress.append(BIN);
        ress.append(randomNumber1);

        String res = BIN + randomNumber1;
        String add = luhnAlgo(res);
        ress.append(add);

        return ress.toString();
    }

    public String generatePin() {
        Random random = new Random();
        String randomPin = String.format("%04d", random.nextInt(10000));
        return randomPin;
    }

    public static String luhnAlgo(String num) {
        String[] cardNum = num.split("");
        int[] cardNumInt = new int[15];
        for (int i = 0; i < cardNum.length; i++) {
            cardNumInt[i] = Integer.valueOf(cardNum[i]);
        }
        int sum = 0;
        for (int i = 0; i < cardNumInt.length; i++) {
            if (i % 2 == 0) {
                cardNumInt[i] = cardNumInt[i] * 2;
            }
            if (cardNumInt[i] / 10 > 0) {
                cardNumInt[i] = cardNumInt[i] - 9;
            }
            sum += cardNumInt[i];
        }
        int add = 0;
        while (sum % 10 != 0) {
            sum += 1;
            add += 1;
        }
        return Integer.toString(add);
    }



    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
