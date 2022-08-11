package banking;

import TestJDBC.TestJDBC;

public class Main {

    static String db;

    public static void main(String[] args) {
        db = args[1];
        TestJDBC.connect();
        BankingMenu.start();
    }

    public static String db() {
        return db;
    }
}