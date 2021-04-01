import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

/**
 * CS261 - Assignment #4-#5 - Summer 2017
 * Created by: Noon Pokaratsiri
 * Version: 08/13/2017
 *
 * base class for other types of Account
 * Account Class, contains a Collection of Transactions as well as the fields for each account
 * such as total amount, num. items etc.
 */
public class Account {
    public enum AccountType {
        CREDIT_CARD, INVESTMENT, REGULAR
    }

    private List<Transaction>      myTransactions;
    private List<Transaction>      foundTransactions;
    private Found                  finding;
    private int                    totalItems;
    private double                 totalIncome;
    private double                 totalExpense;
    private double                 netAmount;
    private AccountType            accountType;
    private ExternalFile           outFile;

    /**
     * default constructor, create a new ArrayList of empty transactions
     */
    public Account() {
        myTransactions = new ArrayList<Transaction>();
        foundTransactions = new ArrayList<Transaction>();
        finding = new Found();
        totalItems = myTransactions.size();
        totalIncome = 0;
        totalExpense = 0;
        netAmount = totalIncome + totalExpense;
        accountType = AccountType.REGULAR;
        outFile = new ExternalFile(accountType);
    }

    /**
     * constructor with arguments
     * @param transactionList is used to create myTranasactions
     * @param accountType is used to select the right txt file
     */
    public Account(List<Transaction> transactionList, AccountType accountType) {
        myTransactions = new ArrayList<Transaction>(transactionList);
        foundTransactions = new ArrayList<Transaction>();
        finding = new Found();
        totalItems = myTransactions.size();
        totalIncome = 0;
        totalExpense = 0;
        for (Transaction tran : myTransactions) {
            setTotalIncomeExpense(tran);
        }
        netAmount = totalIncome + totalExpense;
        setAccountType(accountType);
        setOutFile(accountType);
    }

    /**
     * adding a transaction to Account, can be credit and debit, for credit card account, purchases are considered credit and payments debit
     * @param aTransaction
     */
    public void addTransaction(Transaction aTransaction) {
        myTransactions.add(aTransaction);
        if (aTransaction instanceof Expense && aTransaction.getItemDescription().getCategory() == "Business")
            ((Expense) aTransaction).setDeductible("True");
        setTotalIncomeExpense(aTransaction);
        setNetAmount(totalIncome, totalExpense);
        setTotalItems();
        System.out.println("\nTransaction added.");
    }

    /**
     * removing a transaction by keyWord, remove the FIRST occurrence found
     * @param keyWord String value used in searching for a match
     */
    public void removeTransaction(String keyWord) {
        Transaction foundItem = finding.findItem(keyWord, myTransactions);
        if (foundItem != null) {
            System.out.println("\nRemoving Item No. " + foundItem.getItemNumber() + " " + foundItem.getItemDescription());
            myTransactions.remove(foundItem);
            reduceTotalIncomeExpense(foundItem);
            setNetAmount(totalIncome, totalExpense);
            setTotalItems();
        } else {
            System.out.println("\nFailed to remove - item not found!!!");
        }
    }

    /**
     *
     * @return myTransactions Collection of Transactions
     */
    public List<Transaction> getMyTransactions() {
        return myTransactions;
    }

    /**
     *
     * @return int number of items
     */
    public int getTotalItems() {
        return totalItems;
    }

    /**
     *
     * @return double value total amount of all CREDIT transactions
     * credit refers to deposits/income in a regular account, but purchases in a credit card account
     */
    public double getTotalIncome() {
        return totalIncome;
    }

    /**
     *
     * @return double value total amount of all DEBIT transactions
     * debit refers to expenses/withdrawal in a regular account, but payment/refunds in a credit card account
     */
    public double getTotalExpense() {
        return totalExpense;
    }

    /**
     *
     * @return double value netAmount
     */
    public double getNetAmount() {
        return netAmount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    /**
     *
     * @param interestRate changes totalIncome to reflect/include interestRate
     */
    public void setTotalIncome(double interestRate) {
        this.totalIncome = this.totalIncome + (interestRate * this.totalIncome);
    }

    /**
     *
     * @param accountType1 setAccountType according to what's passed in
     */
    public void setAccountType(AccountType accountType1) {
        this.accountType = accountType1;
    }

    public ExternalFile getOutFile() {
        return outFile;
    }

    /**
     *
     * @param accountType accountType is passed in to determine which txt file in accountMap to read/write
     */
    public void setOutFile(AccountType accountType) {
        this.outFile = new ExternalFile(accountType);
    }

    /**
     *
     * @param aTransaction each time aTransaction is added to the Collection, this method updates totalIncome/Expense
     *                     based on what object aTransaction is
     */
    private void setTotalIncomeExpense(Transaction aTransaction) {
        if (aTransaction instanceof Income || aTransaction.getAmount() > 0)
            totalIncome = totalIncome + aTransaction.getAmount();
        else
            totalExpense = totalExpense + aTransaction.getAmount();
    }

    /**
     *
     * @param foundItem each time a Transaction is found and is removed, this method updates the totalIncome/Expense
     *                  to reflect the updated amount
     */
    private void reduceTotalIncomeExpense(Transaction foundItem) {
        if (foundItem instanceof Income || foundItem.getAmount() > 0)
            totalIncome = totalIncome - foundItem.getAmount();
        else
            totalExpense = totalExpense - foundItem.getAmount();
    }

    /**
     * set net amount which is the sum of totalIncome and totalExpense, one of these values is always < 0
     * @param totalIncome
     * @param totalExpense
     */
    private void setNetAmount(double totalIncome, double totalExpense) {
        this.netAmount = totalIncome + totalExpense;
    }

    /**
     * totalItems is always the same as Collection size
     */
    private void setTotalItems() {
        this.totalItems = myTransactions.size();
    }

    /*public boolean findItem(String keyWord) {

        //for (Iterator it = myTransactions.iterator(); it.hasNext();)
        for (Transaction trans : myTransactions){
            if (trans.getItemDescription().getCategory().equals(keyWord) ||
                    trans.getItemDescription().getDescription().equals(keyWord)) {
                foundTransactions.add(trans);
                System.out.println("\nTransaction found");
            }
        }
        return foundTransactions.size() != 0 ? true : false;
    }*/

    /**
     * find Transaction matching keyWord using Found Class
     * results update foundTransactions Collection
     * @param keyWord
     */
    public void findByCategory(String keyWord) {
        System.out.println("\nShowing Search Result By Category...\n");
        clearFound();
        foundTransactions = finding.findByCategory(keyWord, myTransactions);
        if (foundTransactions.size() != 0)
            printFound();
        else
            System.out.println("No Transactions Found!!!");
    }

    /**
     * find Transaction matching keyWord using Found Class
     * results update foundTransactions Collection
     * @param keyWord
     */
    public void findByKey(String keyWord) {
        System.out.println("\nShowing Search Result By KeyWord...\n");
        clearFound();
        foundTransactions = finding.findByKey(keyWord, myTransactions);
        if (foundTransactions.size() != 0)
            printFound();
        else
            System.out.println("No Transactions Found!!!");
    }

    /**
     * find Transaction matching keyWord using Found Class
     * results update foundTransactions Collection
     * @param keyWord
     */
    public void findByType(String keyWord) {
        System.out.println("\nShowing Search Result By Transaction Type...\n");
        clearFound();
        foundTransactions = finding.findByAmountType(keyWord, myTransactions);
        if (foundTransactions.size() != 0)
            printFound();
        else
            System.out.println("No Transactions Found!!!");
    }

    /**
     * helper function, reset foundTransactions to 0
     */
    private void clearFound() {
        foundTransactions.clear();
    }

    /**
     * display an  item by position in the Collection
     * @param position int position of item in the Collection
     */
    public void print(int position) {
        myTransactions.get(position).print();
    }

    /**
     * display elements in the foundTransactions List
     */
    private void printFound() {
        double netFound = 0;
        for (Transaction tr : foundTransactions) {
            tr.print();
            netFound += tr.getAmount();
        }
        System.out.println("\n\n# of Items Found: " + foundTransactions.size());
        System.out.println(String.format("%s %.2f", "\nNet Amount of Items Found: ", netFound));
        clearFound();
    }

    /**
     * display elements in the myTransactions List
     */
    public void printAll() {
        System.out.println("\nSize of myTransactions/# of items: " + myTransactions.size() + "\n");
        if (myTransactions.size() == 0)
            System.out.println("\nNo Transactions");
        else {
            for (Iterator it = myTransactions.iterator(); it.hasNext(); ) {
            ((Transaction) it.next()).print();
        }
            System.out.println("\nTotal Items:\t" + totalItems + "\n" +
                    "Total Income:\t" + totalIncome + "\n" + "Total Expense:\t" + totalExpense + "\n"
                    + "Net:\t" + netAmount);
        }
    }

    /**
     * write elements in the myTransactions List to external file, using ExternalFile class
     * @param accountType
     */
    public void updateOutFile(AccountType accountType) {
        outFile.writeExternalFile(accountType, myTransactions);
    }


}

