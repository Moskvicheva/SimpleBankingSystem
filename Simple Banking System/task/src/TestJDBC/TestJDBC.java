package TestJDBC;

import banking.Card;
import banking.Main;
import org.sqlite.SQLiteDataSource;

import java.sql.*;

public class TestJDBC {

    static Connection con = null;
    static String database = Main.db();

    public static void connect() {

        String url = "jdbc:sqlite:" + database;

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try {
            con = dataSource.getConnection();

            String sql =  "CREATE TABLE IF NOT EXISTS card ("
                    + " id INTEGER PRIMARY KEY,"
                    + " number TEXT,"
                    + " pin TEXT,"
                    + " balance INTEGER DEFAULT 0"
                    + ")";
            Statement stmt = con.createStatement();
            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insert(Card card) {
        String sql = "INSERT INTO card(number, pin, balance) VALUES(?,?,?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, card.getCardNumber());
                stmt.setString(2, card.getPin());
                stmt.setInt(3, 0);
                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    public static boolean checkCardPin(Card card) {
        String sql = String.format("SELECT * FROM card WHERE number = %s AND pin = %s",
                card.getCardNumber(), card.getPin());
        try (Statement stmt = con.createStatement()) {
            try (ResultSet query = stmt.executeQuery(sql)) {
                return query.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkCard(String number) {
        String sql = String.format("SELECT * FROM card " +
                "WHERE number = %s", number);
        try (Statement stmt = con.createStatement()) {
            try (ResultSet query = stmt.executeQuery(sql)) {
                return query.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void changeBalance(int income, Card card) {
        String sql = String.format(
                "UPDATE card " +
                        "SET balance = %d " +
                        "WHERE number = %s " +
                        "AND pin = %s",
                getBalance(card.getCardNumber()) + income, card.getCardNumber(), card.getPin());
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);

            } catch (SQLException e) {
                e.printStackTrace();
            }

    }

    public static int getBalance(String number){
        String sql = String.format("SELECT balance FROM card " +
                "WHERE number = %s", number);
        try (Statement stmt = con.createStatement()) {
            try (ResultSet query = stmt.executeQuery(sql)) {
                return query.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void transfer(Card card, String number, int money) {
        String sqlFirst = String.format("UPDATE card SET balance = %d WHERE number = %s",
                getBalance(card.getCardNumber()) - money, card.getCardNumber());
        String sqlSecond = String.format("UPDATE card SET balance = %d WHERE number = %s",
                getBalance(number) + money, number);
        try (Statement stmt = con.createStatement()) {
            con.setAutoCommit(false);
            stmt.executeUpdate(sqlFirst);
            stmt.executeUpdate(sqlSecond);
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeAccount(Card card) {
        String sql = String.format("DELETE FROM card WHERE number = %s", card.getCardNumber());
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
