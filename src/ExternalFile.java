import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * this class manages reading data from an external .txt file and writing data to an external .txt file
 * multiple txt files are stored in a Key Value Map
 *
 * CS261 - Assignment #4-#5 - Summer 2017
 * Created by: Noon Pokaratsiri, with ideas/example codes from Joanne McBride
 * Version: 08/13/2017
 */

public class ExternalFile {
    private Map<Account.AccountType, String> accountMap;
    private String                           creditCardFile = "resourceFiles/creditCard.txt";
    private String                           investmentAccountFile = "resourceFiles/investment.txt";
    private String                           accountFile = "resourceFiles/accountFile.txt";

    /**
     * Constructor for ExternalFile
     * @param accountType passed in enum accountType to map the corresponding text file
     */
    ExternalFile(Account.AccountType accountType) {
        accountMap = new HashMap<>();

        if (accountType == Account.AccountType.CREDIT_CARD) {
            accountMap.put(Account.AccountType.CREDIT_CARD, creditCardFile);
        }
        else if (accountType == Account.AccountType.INVESTMENT){
            accountMap.put(Account.AccountType.INVESTMENT, investmentAccountFile);
        } else if (accountType == Account.AccountType.REGULAR) {
            accountMap.put(Account.AccountType.REGULAR, accountFile);
        } else {
            System.out.println("\nInvalid Account type!");
        }
    }

    /**
     * write/update text file in accountMap
     * @param accountType either CREDIT_CARD or INVESTMENT to match the corresponding text file
     * @param transactionList passed in List Collection to read fields to file
     */
    public void writeExternalFile(Account.AccountType accountType, List<Transaction> transactionList) {
        String fileName = accountMap.get(accountType);

        String category;
        String description;
        double amount;
        String transactionType;

        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            System.out.println("Attempted to write transactions to " + fileName);

            for (Transaction transaction : transactionList) {
                category = transaction.getItemDescription().getCategory();
                description = transaction.getItemDescription().getDescription();
                amount = transaction.getAmount();
                transactionType = enumToString(transaction.getItemType());

                printWriter.println(category + ";" + description + ";" + amount + ";" + transactionType);
            }
            System.out.println(fileName + " has been updated.");
            printWriter.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("Unable to find file " + fileName);
        }
        catch (IOException e) {
            System.out.println("Error processing file " + fileName + " ");
            e.printStackTrace();
        }
    }

    /**
     * read fields from txt file, ";' delimited; used to populate Collection of Transactions
     * @param accountType CREDIT_CARD or INVESTMENT are the 2 types of txt files in src
     * @return Collection/List of Transactions
     */
    public List<Transaction> readExternalFile (Account.AccountType accountType) {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        String fileName = accountMap.get(accountType);

        String category;
        String description;
        double amount;
        String transactionType;

        try {
            FileReader     fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                String temp[] = line.split(";");
                category = temp[0];
                description = temp[1];
                amount = Double.parseDouble(temp[2]);
                transactionType = temp[3];

                System.out.println("Attempting to add: " + category + " - " + description + "\t" + amount + "\t" + transactionType);
                if (transactionType.contains("CREDIT"))
                    transactionList.add(new Income(category, description, amount));
                else if (transactionType.contains("DEBIT"))
                    transactionList.add(new Expense(category, description, amount));
                else
                    System.out.println("Unable to add transaction from file - Unknown transactionType!!!");
            }

            bufferedReader.close();
        }

        catch (FileNotFoundException e) {
            System.out.println("Unable to find file " + fileName);
        }
        catch (IOException e) {
            System.out.println("Error processing file " + fileName + " ");
            e.printStackTrace();
        }

        return transactionList;
    }

    /**
     * Convert enum values to String
     * @param transactionType enum INCOME or EXPENSE, INCOME is any amount > 0, and EXPENSE is amount < 0
     * @return String equivalent of the enum e.g. INCOME returns CREDIT
     */
    private String enumToString(Transaction.TransactionType transactionType) {
        switch (transactionType) {
            case INCOME:
                return "CREDIT";
            case EXPENSE:
                return "DEBIT";
            default:
                return "OTHER";
        }
    }




}

