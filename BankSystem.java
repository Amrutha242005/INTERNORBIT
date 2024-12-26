import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Transaction {
    private String type;
    private double amount;
    private String timestamp;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return type + " of $" + amount + " on " + timestamp;
    }
}

class BankAccount {
    private String accountNumber;
    private String name;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public BankAccount(String accountNumber, String name, double initialBalance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add(new Transaction("Deposit", amount));
            System.out.println("Deposit successful. New balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            System.out.println("Withdrawal successful. New balance: $" + balance);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public void checkBalance() {
        System.out.println("Current balance: $" + balance);
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction t : transactionHistory) {
                System.out.println(t);
            }
        }
    }

    public void calculateInterest(double interestRate, int years) {
        double interest = balance * (interestRate / 100) * years;
        System.out.println("Interest for " + years + " years at " + interestRate + "%: $" + interest);
    }
}

class User {
    private String username;
    private String password;
    private BankAccount account;

    public User(String username, String password, BankAccount account) {
        this.username = username;
        this.password = password;
        this.account = account;
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public BankAccount getAccount() {
        return account;
    }
}

public class BankSystem {
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        BankAccount account = new BankAccount("33452789", "Shiva", 1000.0);
        users.add(new User("Shiv", "pass123", account));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Bank Management System");
      
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User currentUser = authenticateUser(username, password);
        if (currentUser == null) {
            System.out.println("Invalid credentials. Exiting...");
            return;
        }

        BankAccount userAccount = currentUser.getAccount();
        int choice;

        do {
            System.out.println("\n1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Transaction History");
            System.out.println("5. Calculate Interest");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
          
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    userAccount.deposit(depositAmount);
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();
                    userAccount.withdraw(withdrawalAmount);
                    break;
                case 3:
                    userAccount.checkBalance();
                    break;
                case 4:
                    userAccount.showTransactionHistory();
                    break;
                case 5:
                    System.out.print("Enter interest rate (%): ");
                    double interestRate = scanner.nextDouble();
                    System.out.print("Enter number of years: ");
                    int years = scanner.nextInt();
                    userAccount.calculateInterest(interestRate, years);
                    break;
                case 6:
                    System.out.println("Thank you for using the Bank Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private static User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.authenticate(username, password)) {
                return user;
            }
        }
        return null;
    }
}
