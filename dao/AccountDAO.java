package dao;

import java.sql.*;
import db.DBConnection;
import model.Account;
import util.PasswordUtil;

public class AccountDAO {

    // ================= CREATE ACCOUNT =================
    public Account createAccount(String name, String password, double balance, String role) {

        String sql = "INSERT INTO accounts (name, password, balance, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            pstmt.setString(2, PasswordUtil.hash(password));
            pstmt.setDouble(3, balance);
            pstmt.setString(4, role);

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int accNo = rs.getInt(1);
                return new Account(accNo, name, balance, role);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= LOGIN =================
    public Account login(int accNo, String password) throws Exception {

        String sql = "SELECT * FROM accounts WHERE accountNo=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accNo);
            pstmt.setString(2, PasswordUtil.hash(password));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("accountNo"),
                        rs.getString("name"),
                        rs.getDouble("balance"),
                        rs.getString("role"));
            }
        }
        return null;
    }

    // ================= DEPOSIT =================
    public void deposit(int accNo, double amount) throws Exception {

        if (amount <= 0)
            throw new Exception("Invalid deposit amount");

        String sql = "UPDATE accounts SET balance = balance + ? WHERE accountNo=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accNo);

            if (pstmt.executeUpdate() == 0)
                throw new Exception("Account not found");

            // ✅ AFTER update → read actual balance
            double newBalance = checkBalance(accNo);
            TransactionDAO.save(accNo, "DEPOSIT", amount, newBalance);
        }
    }

    // ================= WITHDRAW =================
    public void withdraw(int accNo, double amount) throws Exception {

        if (amount <= 0)
            throw new Exception("Invalid withdrawal amount");

        double balance = checkBalance(accNo);
        if (balance < amount)
            throw new Exception("Insufficient balance");

        String sql = "UPDATE accounts SET balance = balance - ? WHERE accountNo=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accNo);
            pstmt.executeUpdate();

            // ✅ AFTER update
            double newBalance = checkBalance(accNo);
            TransactionDAO.save(accNo, "WITHDRAW", amount, newBalance);
        }
    }

    // ================= TRANSFER (TRANSACTION SAFE) =================
    public void transfer(int fromAcc, int toAcc, double amount) throws Exception {

        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            // FROM account
            withdraw(fromAcc, amount);
            TransactionDAO.save(
                    fromAcc,
                    "TRANSFER_OUT",
                    amount,
                    checkBalance(fromAcc));

            // TO account
            deposit(toAcc, amount);
            TransactionDAO.save(
                    toAcc,
                    "TRANSFER_IN",
                    amount,
                    checkBalance(toAcc));

            conn.commit();

        } catch (Exception e) {
            conn.rollback();
            throw new Exception("Transfer failed: " + e.getMessage());
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    // ================= CHECK BALANCE =================
    public double checkBalance(int accNo) throws Exception {

        String sql = "SELECT balance FROM accounts WHERE accountNo=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accNo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
                return rs.getDouble("balance");

            throw new Exception("Account not found");
        }
    }

    // ================= DELETE ACCOUNT (ADMIN) =================
    public boolean deleteAccount(int accNo) throws Exception {

        String sql = "DELETE FROM accounts WHERE accountNo=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accNo);
            return pstmt.executeUpdate() > 0;
        }
    }

    // ================= CHANGE PASSWORD =================
    public void changePassword(int accNo, String newPassword) throws Exception {

        String sql = "UPDATE accounts SET password=? WHERE accountNo=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, PasswordUtil.hash(newPassword));
            pstmt.setInt(2, accNo);
            pstmt.executeUpdate();
        }
    }
}
