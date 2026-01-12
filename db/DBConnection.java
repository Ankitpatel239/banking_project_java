package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:bank.db";

    public static Connection getConnection() {
        Connection conn = null;
        System.out.println("Connecting to SQLite database...");
        try {

            Class.forName("org.sqlite.JDBC");

            conn = DriverManager.getConnection(URL);
            createTable(conn);
            System.out.println("Connected to SQLite successfully");

        } catch (Exception e) {
            System.out.println("SQLite connection failed: " + e.getMessage());
        }
        return conn;
    }

    private static void createTable(Connection conn) {
        String sql = """
                    CREATE TABLE IF NOT EXISTS accounts (
                        accountNo INTEGER PRIMARY KEY AUTOINCREMENT,
                        name TEXT NOT NULL,
                        password TEXT NOT NULL,
                        balance REAL NOT NULL,
                        role TEXT NOT NULL
                    );
                """;
        String txTable = """
                CREATE TABLE IF NOT EXISTS transactions (
                    txId INTEGER PRIMARY KEY AUTOINCREMENT,
                    accountNo INTEGER NOT NULL,
                    type TEXT NOT NULL,
                    amount REAL NOT NULL,
                    balanceAfter REAL NOT NULL,
                    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
                );
                """;

       

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
             stmt.execute(txTable);
        } catch (Exception e) {
            System.out.println("Table error: " + e.getMessage());
        }
    }
}
