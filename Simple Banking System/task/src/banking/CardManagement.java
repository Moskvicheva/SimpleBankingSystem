package banking;

import TestJDBC.TestJDBC;

import java.util.Scanner;

public class CardManagement {

    static Card card = new Card();
    static final Scanner sc = new Scanner(System.in);

    public static void createAccount() {
        Card cardNew = new Card();
        System.out.println("Your card has been created");
        System.out.println("Your card number: ");
        System.out.println(cardNew.getCardNumber());
        System.out.println("Your card PIN: ");
        System.out.println(cardNew.getPin());

        TestJDBC.insert(cardNew);
    }

    public static void logIn() {
        System.out.println("Enter your card number: ");
        String num = sc.next();
        System.out.println("Enter your PIN: ");
        String pin = sc.next();
        card.setCardNumber(num);
        card.setPin(pin);

        if (TestJDBC.checkCardPin(card)) {
            System.out.println("You have successfully logged in!");
        } else {
            System.out.println("Wrong card number or PIN!");
        }
    }

    public static void cardBalance() {
        System.out.println("Balance : " + card.getBalance());
    }

    public static void addIncome() {
        System.out.println("Enter income: ");
        int income = sc.nextInt();
        TestJDBC.changeBalance(income, card);
        System.out.println("Income was added!");
    }

    public static void transfer() {
        System.out.println("Transfer \nEnter card number: ");
        String number = sc.next();
        String numberCheck = number.substring(0, 15);
        if (!Card.luhnAlgo(numberCheck).equals(number.substring(15))) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        } else if (number.equals(card.getCardNumber())) {
            System.out.println("You can't transfer money to the same account!");
        } else {
            if (!TestJDBC.checkCard(number)) {
                System.out.println("Such a card does not exist.");
            } else {
                System.out.println("Enter how much money you want to transfer: ");
                int money = sc.nextInt();
                if (TestJDBC.getBalance(card.getCardNumber()) < money) {
                    System.out.println("Not enough money!");
                } else {
                    TestJDBC.transfer(card, number, money);
                    System.out.println("Success!");
                }
            }
        }
    }

    public static void closeAccount() {
        TestJDBC.closeAccount(card);
        System.out.println("The account has been closed!");
    }



    public static void logOut() {
        System.out.println("You have successfully logged out!");
    }



    public static void exit() {
        System.out.println("Bye!");
    }
}
