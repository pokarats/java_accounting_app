import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * CS261 - Assignment #4-#5 - Summer 2017
 * Created by: Noon Pokaratsiri
 * Version: 08/13/2017
 *
 * Driver Class where the main method is
 * TestAccount class tests different methods in the Account, InterestAccount, Found, ExternalFile, and
 * Transaction Classes
 */
public class TestAccount {
    public static void main(String[] args) {

        String  accountMode = selectMode();
        Account testAccount = null;
        Account.AccountType accountType = Account.AccountType.REGULAR;

        if (accountMode.contains("R")) {
            ExternalFile outputFile = new ExternalFile(accountType);
            List<Transaction> transactionList = new ArrayList<Transaction>(outputFile.readExternalFile(accountType));
            testAccount = new Account(transactionList, accountType);
        } else if (accountMode.contains("I")) {
            accountType = Account.AccountType.CREDIT_CARD;
            ExternalFile outputFile = new ExternalFile(accountType);
            List<Transaction> transactionList = new ArrayList<Transaction>(outputFile.readExternalFile(accountType));
            testAccount = new InterestAccount(transactionList, accountType);
        } else {
            System.out.println("Invalid Mode Selection!!! \nTerminating program");
            System.exit(1);
        }

        String    response;
        Scanner   userInput = new Scanner(System.in);

        do {
            /*System.out.println("!!!Welcome to My Accounting App!!!" + "\nPlease select from the following menu: " +
                    "\n\nA) Add Transaction" + "\nS) Search Transaction" + "\nD) Display Transactions" +
                    "\nR) Remove Transaction" + "\nV) View Demo" + "\nQ) Quit");
            response = userInput.next().toUpperCase();*/
            response = JOptionPane.showInputDialog("Welcome to My Accounting App \nPlease choose from the menu below \nA) Add \n" +
                    "S) Search  \nD) Display \nR) Remove \nV) View Demo \nQ) Quit");
            response = response.toUpperCase();
            System.out.println("Your Selection is: " + response);
            selection(response, testAccount);

        } while (!(response.contains("Q")));
        userInput.close();
        testAccount.updateOutFile(accountType);
        System.exit(0);
    }

    /**
     *  Soliciting user for input to select what type of account to work in: Regular or InterestAccount
     * @return String representation of the respond provided by user
     */
    public static String selectMode() {
        String accountMode;

        accountMode = JOptionPane.showInputDialog("Please Choose Account Type: (R)egular or (I)nterest \n(Type the Letter in ( ) to Respond.");
        accountMode = accountMode.toUpperCase();

        return accountMode;
    }

    /**
     * provides control flow of the app based on user's selection of which methods/Classes to test
     * @param response user's response String
     * @param anAccount Account newed in main, passed in to be tested based on user's selection
     */
    public static void selection(String response, Account anAccount) {
        switch (response) {
            case "A":
                demoAdd(anAccount);
                break;
            case "S":
                demoSearch(anAccount);
                break;
            case "D":
                anAccount.printAll();
                break;
            case "R":
                demoRemove(anAccount);
                break;
            case "V":
                demoAccount();
                break;
            case "Q":
                System.out.println("\nThank you for using!! Quitting App...");
                return;
            default:
                System.out.println("\nInvalid Selection...");
        }
        System.out.println("\nPlease select your next action to continue. Q/q to quit...");
    }

    /**
     * Testing add methods, allowing user interactions with external file read/write support
     * adding a new transaction to Account based on user provided data
     * @param anAccount Account to be tested on
     */
    public static void demoAdd(Account anAccount) {
        String category = JOptionPane.showInputDialog("Enter Category: ");
        String description = JOptionPane.showInputDialog("Enter Description: ");
        String strAmount = JOptionPane.showInputDialog("Enter Amount: \n(No ',' - No space - No symbols");
        String transactionType = JOptionPane.showInputDialog("Enter Transaction Type: \n(Type: CREDIT or DEBIT)");

        double amount = Double.parseDouble(strAmount);
        transactionType = transactionType.toUpperCase();

        if (transactionType.contains("CREDIT"))
            anAccount.addTransaction(new Income(category, description, amount));
        else if (transactionType.contains("DEBIT"))
            anAccount.addTransaction(new Expense(category, description, amount));
        else
            System.out.println("Failed to add - Unknown Transaction Type!!!");
    }

    /**
     * Testing search methods, allowing user interactions with external file read/write support
     * @param anAccount Account to be tested on
     */
    public  static void demoSearch(Account anAccount) {
        String searchOption = JOptionPane.showInputDialog("Enter Search Option: \n(Type: Category, Type, or Key to respond)");
        searchOption = searchOption.toUpperCase();
        String keyWord = JOptionPane.showInputDialog("Enter Search Key: ");

        switch (searchOption) {
            case "CATEGORY":
                anAccount.findByCategory(keyWord);
                break;
            case "TYPE":
                anAccount.findByType(keyWord);
                break;
            case "KEY":
                anAccount.findByKey(keyWord);
                break;
            default:
                System.out.println("\nAborted...Invalid Search Option!!!");
        }
    }

    /**
     * Testing remove method, allowing user interactions with external file read/write support
     * removing a transaction from Account
     * @param anAccount Account to be tested on
     */
    public static void demoRemove(Account anAccount) {
        String keyWord = JOptionPane.showInputDialog("Enter KeyWord to Remove Item: ");
        anAccount.removeTransaction(keyWord);
        anAccount.printAll();
    }

    /**
     * Testing the different methods without user's interaction; simulation only
     * This is the Step 1 of the Assignment #4-#5
     * no external .txt file is read or written in this option
     */
    public static void demoAccount() {
        System.out.println("Testing the Account Class and the Transaction Classes" +
                            "\nStep 1 of Assignment #4-#5");

        Account myAccount = new Account();

        Transaction transaction1 = new Income("Personal", "PayCheck", 1500);
        Transaction transaction2 = new Income("Business", "AirBnB Rental July", 850);
        Transaction transaction3 = new Income("Personal", "PayCheck2", 1500);
        Transaction transaction4 = new Expense(25);
        Transaction transaction5 = new Expense("Restaurant", "Dinner with friends", 42);
        Transaction transaction6 = new Expense("Restaurant", "Lunch at Work", 12);
        Transaction transaction7 = new Expense("Business", "Supplies", 35);
        Transaction transaction8 = new Expense(35);
        Transaction transaction9 = new Expense(540);
        Transaction transaction10 = new Expense("Business", "Misc", 250);

        myAccount.printAll();
        myAccount.addTransaction(transaction1);
        myAccount.printAll();
        myAccount.addTransaction(transaction2);
        myAccount.printAll();
        myAccount.addTransaction(transaction3);
        myAccount.printAll();
        myAccount.addTransaction(transaction4);
        myAccount.addTransaction(transaction5);
        myAccount.addTransaction(transaction6);
        myAccount.addTransaction(transaction7);
        myAccount.addTransaction(transaction8);
        myAccount.addTransaction(transaction9);
        myAccount.addTransaction(transaction10);
        myAccount.printAll();

        System.out.println("Testing printing individual transactions\n");
        myAccount.print(0);
        myAccount.print(2);
        myAccount.print(5);

        System.out.println("Testing utility class Found\n");

        myAccount.findByCategory("Business");
        myAccount.findByType("Credit");
        myAccount.findByType("Debit");
        myAccount.findByKey("Pay");

        System.out.println("\nTesting removing individual transactions\n");
        myAccount.removeTransaction("Dinner");
        myAccount.printAll();

        myAccount.removeTransaction("No");
        myAccount.printAll();

        myAccount.removeTransaction("Ginza");
        myAccount.printAll();
    }
}

