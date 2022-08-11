package banking;

import TestJDBC.TestJDBC;

import java.util.Scanner;

public class BankingMenu {

    static boolean notLoggedIn = true;
    static boolean exit = false;

    public static void start() {
        TestJDBC.connect();

        while (!exit) {
            if (notLoggedIn) {
                BankingMenu.menuIfNotLogged();
            } else {
                BankingMenu.menuIfLogged();
            }
            Scanner sc = new Scanner(System.in);
            switch (sc.nextInt()) {
                case 1:
                    if (notLoggedIn) {
                        CardManagement.createAccount();
                    } else {
                        CardManagement.cardBalance();
                    }
                    break;
                case 2:
                    if (notLoggedIn) {
                        CardManagement.logIn();
                        notLoggedIn = false;
                    } else {
                        CardManagement.addIncome();
                    }
                    break;

                case 3:
                    if (notLoggedIn) {
                        break;
                    } else {
                        CardManagement.transfer();
                    }
                    break;
                case 4:
                    if (notLoggedIn) {
                        break;
                    } else {
                        CardManagement.closeAccount();
                        notLoggedIn = true;
                    }
                    break;
                case 5:
                    if (notLoggedIn) {
                        break;
                    } else {
                        CardManagement.logOut();
                        notLoggedIn = true;
                    }
                    break;
                default:
                    CardManagement.exit();
                    exit = true;
                    break;
            }
        }
    }

    public static void menuIfNotLogged() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");
    }

    public static void menuIfLogged() {
        System.out.println("1. Balance");
        System.out.println("2. Log out");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
    }

}
