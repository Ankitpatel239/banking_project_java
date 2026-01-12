import java.util.Scanner;

import model.Account;
import service.BankService;

public class Main {
      public static void main(String[] args) throws Exception {
        BankService service = new BankService();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n 1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("Choose : ");

            int choice = sc.nextInt();

            if (choice == 1) {
                sc.nextLine();
                System.out.print("Name : ");
                String name = sc.nextLine();
                System.out.print("Password : ");
                String password = sc.nextLine();
                System.out.print("Initial Balance : ");
                double balance = sc.nextDouble();
                System.out.print("Role (ADMIN/CUSTOMER): ");
                String role = sc.next();
                int accNo = service.register(name, password, balance, role);
                if (accNo != -1) {
                    System.out.println("Account created successfully! Your Account No is: " + accNo);
                } else {
                    System.out.println("Account creation failed.");
                }
            } else if (choice == 2) {
                 sc.nextLine();
                System.out.print("\n Account No : ");
                int accNo = sc.nextInt();
                sc.nextLine(); // consume the leftover newline
                System.out.print("Password : ");
                String pass = sc.nextLine();
               Account account = service.login(accNo, pass);
                if (account != null) {
                     System.out.println("Login Successful! Welcome, " + account.getName() + " (" + account.getRole() + ")");

                     while (true) {
                            System.out.println("\n 1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Transfer");
                            System.out.println("4. Check Balance");
                            System.out.println("5. Mini Statement");
                            System.out.println("6. Export Statement to CSV");
                            System.out.println("7. Logout");
                            System.out.print("Choose : ");
    
                            int subChoice = sc.nextInt();
    
                            if (subChoice == 1) {
                                System.out.print("Amount to Deposit: ");
                                double amount = sc.nextDouble();
                                service.deposit(accNo, amount);
                            } else if (subChoice == 2) {
                                System.out.print("Amount to Withdraw: ");
                                double amount = sc.nextDouble();
                                service.withdraw(accNo, amount);
                                System.out.println("Withdrawn Successfully!");
                            } else if (subChoice == 3) {
                                System.out.print("Transfer To Account No: ");
                                int toAccNo = sc.nextInt();
                                System.out.print("Amount to Transfer: ");
                                double amount = sc.nextDouble();
                                service.transfer(accNo, toAccNo, amount);
                                System.out.println("Transferred Successfully!");
                            } else if (subChoice == 4) {
                                double balance = service.checkBalance(accNo);
                                System.out.println("Current Balance: " + balance);
                                
                            } else if (subChoice == 5) {
                                service.miniStatement(accNo);
                                
                            } else if (subChoice == 6) {
                                service.exportStatement(accNo);
                            } else if (subChoice == 7) {
                                System.out.println("Logging out...");
                                break;
                            } else {
                                System.out.println("Invalid Choice!");
                            }
                     }
                } else {
                     System.out.println("Invalid Account No or Password.");
                }
            } else {
                System.out.println("Exiting...");
                sc.close();
                System.exit(0);

            }

        }

    }
}
