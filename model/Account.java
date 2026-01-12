package model;

public class Account {
    private int accountNo;
    private String name;
    private double balance;
    private String role;

    public Account (int accountNo, String name, double balance, String role) {
        this.accountNo = accountNo;
        this.name = name;
        this.balance = balance;
        this.role = role;
    }

    public int getAccountNo() {return accountNo;}
    public String getName() {return name;}
    public double getBalance() {return balance;}
    public String getRole() {return role;}
}
