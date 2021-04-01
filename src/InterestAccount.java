import java.util.Iterator;
import java.util.List;

public class InterestAccount extends Account {
    private static final double INTEREST_RATE = 0.18;

    InterestAccount(List<Transaction> transactionList, AccountType accountType) {
        super(transactionList, accountType);

    }

    /**
     * Changes TotalIncome to include Interest Rate
     * @param INTEREST_RATE pass in constant value INTEREST_RATE to parent class function
     */
    @Override
    public void setTotalIncome(double INTEREST_RATE) {
        super.setTotalIncome(INTEREST_RATE);
    }

    /**
     * override printAll method to print relevant information for the InterestAccount Class
     */
    @Override
    public void printAll() {
        System.out.println("\nSize of myTransactions/# of items: " + getMyTransactions().size() + "\n");
        if (getMyTransactions().size() == 0)
            System.out.println("\nNo Transactions");
        else {
            for (Iterator it = getMyTransactions().iterator(); it.hasNext(); ) {
                ((Transaction) it.next()).print();
            }
            System.out.println("\nTotal Items:\t" + getTotalItems() + "\n" +
                    "Total Credit + Interest:\t" + getTotalIncome() + "\n" + "Total Debit:\t" + getTotalExpense() + "\n"
                    + "Net:\t" + getNetAmount());
        }
    }
}
