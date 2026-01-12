# 🚀 Java Banking System (DAO Project)

A hands-on Java Banking System designed to master Core Java, JDBC, and secure database transactions.  
Built with the DAO (Data Access Object) pattern for robust, maintainable, and transaction-safe operations.

---

## 🎯 Project Objective

- Deepen Java & JDBC expertise
- Apply the DAO design pattern
- Implement transaction management (commit & rollback)
- Practice secure password handling
- Work with SQL databases

---

## ✨ Features

- 🏦 Create bank account
- 🔒 Secure login (hashed passwords)
- 💰 Deposit & Withdraw money
- 🔄 Transfer funds between accounts
- 📊 Check account balance
- 🔑 Change password
- 🗑️ Delete account (Admin only)
- 📜 Transaction history

---

## 🛠️ Technologies Used

- Java
- JDBC
- SQL (MySQL / PostgreSQL / SQLite)
- DAO Design Pattern
- PreparedStatement

---

## 📁 Project Structure

```
Banking_Project_Java/
│
├── db/
│   └── DBConnection.java
│
├── model/
│   └── Account.java
│   └── Transaction.java
│
├── lib/
│   └── sqlite-jdbc-3.51.1.0.jar
│
├── util/
│   └── PasswordUtil.java
│   └── CSVUtil.java     
│
├── dao/
│   └── AccountDAO.java
│   └── TransactionDAO.java
│
├── service/
│   └── BankService.java
│
└── Main.java
```

---

## 🧩 Component Overview

### `AccountDAO`
Handles all account operations: create, login, deposit, withdraw, transfer, and balance check.

### `TransactionDAO`
Manages transaction records for deposits, withdrawals, and transfers.

### `DBConnection`
Centralizes database connectivity.  
Uses `sqlite-jdbc-3.51.1.0.jar` for SQLite.  
[Download here](https://github.com/xerial/sqlite-jdbc).

### `PasswordUtil`
Hashes passwords for secure storage.

---

## 🔐 Security Highlights

- Passwords stored as hashes
- SQL Injection prevention via PreparedStatement
- Transaction safety (commit & rollback)

---

## 🔄 Transaction Handling

Transfers are transaction-safe:
1. Disable auto-commit
2. Withdraw from sender
3. Deposit to receiver
4. Commit if successful
5. Rollback on error

---

## 🗄️ Sample Database Schema

```sql
CREATE TABLE accounts (
    accountNo INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    password VARCHAR(255),
    balance DOUBLE,
    role VARCHAR(20)
);

CREATE TABLE transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    accountNo INT,
    type VARCHAR(50),
    amount DOUBLE,
    balance DOUBLE,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## ▶️ How to Run

1. **Clone** the repository
2. **Open** in Eclipse or IntelliJ IDEA
3. **Configure** DB details in `DBConnection.java`
4. **Create** database tables
5. **Run** `Main.java`

---

## 📚 Learning Outcomes

- JDBC & database operations
- DAO design pattern
- SQL transactions
- Java exception handling
- Secure password management

---

## 🚧 Future Improvements

- Console/GUI user interface
- Transaction history view
- Role-based access control
- Logging
- Unit testing
- Spring Boot implementation

---

## 📄 License

Open-source project for educational purposes.

---
## 🤝 Contributing

Contributions are welcome! Please fork the repository and submit a pull request.
# See .gitignore for ignored files
# Compiled class files
*.class
*.csv
*.db
