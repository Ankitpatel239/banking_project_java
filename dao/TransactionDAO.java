package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.DBConnection;
import model.Transaction;

public class TransactionDAO {

    // SAVE TRANSACTION
    public static void save(int accNo, String type, double amount, double balanceAfter) {

        String sql = "INSERT INTO transactions(accountNo, type, amount, balanceAfter) VALUES(?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accNo);
            pstmt.setString(2, type);
            pstmt.setDouble(3, amount);
            pstmt.setDouble(4, balanceAfter);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MINI STATEMENT (LAST N TRANSACTIONS)
    public static List<Transaction> getMiniStatement(int accNo, int limit) throws Exception {

        String sql = """
            SELECT * FROM transactions
            WHERE accountNo = ?
            ORDER BY txId DESC
            LIMIT ?
        """;

        List<Transaction> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accNo);
            pstmt.setInt(2, limit);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Transaction(
                        rs.getInt("txId"),
                        rs.getInt("accountNo"),
                        rs.getString("type"),
                        rs.getDouble("amount"),
                        rs.getDouble("balanceAfter"),
                        rs.getString("timestamp")
                ));
            }
        }
        return list;
    }

    // FULL STATEMENT (ALL TRANSACTIONS)
    public static List<Transaction> getAllTransactions(int accNo) throws Exception {

    String sql = """
        SELECT * FROM transactions
        WHERE accountNo = ?
        ORDER BY txId
    """;

    List<Transaction> list = new ArrayList<>();

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, accNo);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            list.add(new Transaction(
                    rs.getInt("txId"),
                    rs.getInt("accountNo"),
                    rs.getString("type"),
                    rs.getDouble("amount"),
                    rs.getDouble("balanceAfter"),
                    rs.getString("timestamp")
            ));
        }
    }
    return list;
}

}
