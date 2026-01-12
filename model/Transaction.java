package model;

public class Transaction {
    private int txId;
    private int accountNo;
    private String type;
    private double amount;
    private double balanceAfter;
    private String timestamp;

    public Transaction(int txId, int accountNo, String type,
                       double amount, double balanceAfter, String timestamp) {
        this.txId = txId;
        this.accountNo = accountNo;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format(
            "%-5d %-12s %-10.2f %-12.2f %s",
            txId, type, amount, balanceAfter, timestamp
        );
    }

    public int getTxId() { return txId; }
public int getAccountNo() { return accountNo; }
public String getType() { return type; }
public double getAmount() { return amount; }
public double getBalanceAfter() { return balanceAfter; }
public String getTimestamp() { return timestamp; }
}
