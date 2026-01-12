package service;

import java.util.List;

import dao.AccountDAO;
import dao.TransactionDAO;
import model.Account;
import model.Transaction;
import util.CSVUtil;

public class BankService {
    AccountDAO accountDAO = new AccountDAO();

    public int register(String name, String password, double balance, String role) throws Exception {
        Account account = accountDAO.createAccount(name, password, balance, role);
        if (account != null) {
            return account.getAccountNo();
        }
        return -1; // or throw an exception
    }

    public Account login(int accNo, String password) throws Exception {
        return accountDAO.login(accNo, password);

    }

    public void deposit(int accNo, double amount) throws Exception {
        accountDAO.deposit(accNo, amount);
        System.out.println("Deposited Successfully!");
    }

    public void withdraw(int accNo, double amount) throws Exception {
        accountDAO.withdraw(accNo, amount);
        System.out.println("Withdrawn Successfully!");
    }

    public void transfer(int fromAccNo, int toAccNo, double amount) throws Exception {
        accountDAO.transfer(fromAccNo, toAccNo, amount);
        System.out.println("Transferred Successfully!");
    }

    public double checkBalance(int accNo) throws Exception {
        return accountDAO.checkBalance(accNo);
    }

    public void miniStatement(int accNo) throws Exception {

        List<Transaction> list = TransactionDAO.getMiniStatement(accNo, 5);

        System.out.println("\n---- MINI STATEMENT ----");
        System.out.println("ID    TYPE         AMOUNT     BALANCE     DATE");

        for (Transaction t : list) {
            System.out.println(t);
        }

    }

  public void exportStatement(int accNo) throws Exception {

    var transactions = TransactionDAO.getAllTransactions(accNo);

    if (transactions.isEmpty()) {
        System.out.println("No transactions found");
        return;
    }

    String fileName = "statement_" + accNo + ".csv";
    CSVUtil.exportTransactionsToCSV(transactions, fileName);
}
}