import java.util.ArrayList;
import java.util.List;

/**
 * utility class to perform different search/find methods
 *
 *
 * CS261 - Assignment #4-#5 - Summer 2017
 * Created by: Noon Pokaratsiri, with ideas/example codes from Joanne McBride
 * Version: 08/13/2017
 */

public class Found {

    /**
     * Search for the first Transaction in List matching keyWord
     * @param keyWord search key String
     * @param transactionList the Collection to search on
     * @return individual Transaction matching String, otherwise return null
     */
    public Transaction findItem(String keyWord, List<Transaction> transactionList) {
        for (Transaction transaction : transactionList) {
            if (transaction.getItemDescription().getDescription().contains(keyWord) ||
                    transaction.getItemDescription().getCategory().equals(keyWord)) {
                System.out.println("\nItem found");
                return transaction;
            }
        }
        System.out.println("\nItem NOT found");
        return null;
    }

    /**
     * Search for all Transactions in List matching keyWord in Category
     * @param keyWord search key String
     * @param transactionList the Collection to search on
     * @return Collection of Transactions matching keyWord
     */
    public List<Transaction> findByCategory(String keyWord, List<Transaction> transactionList) {
        List<Transaction> foundList = new ArrayList<Transaction>();

        for (Transaction transaction : transactionList) {
            if (transaction.getItemDescription().getCategory().equals(keyWord)) {
                foundList.add(transaction);
            }
        }

        return foundList;
    }

    /**
     * Search for all Transactions in List matching keyWord
     * @param keyWord search key String
     * @param transactionList the Collection to search on
     * @return Collection of Transactions matching keyWord
     */
    public List<Transaction> findByKey(String keyWord, List<Transaction> transactionList) {
        List<Transaction> foundList = new ArrayList<Transaction>();

        for (Transaction transaction : transactionList) {
            if (transaction.getItemDescription().getDescription().contains(keyWord) ||
                    transaction.getItemDescription().getCategory().contains(keyWord)) {
                foundList.add(transaction);
            }
        }

        return foundList;
    }

    /**
     * Search for the all Transactions in List matching keyWord by AmountType (debit or credit)
     * @param keyWord search key String
     * @param transactionList the Collection to search on
     * @return Collection of Transactions matching keyWord
     */
    public List<Transaction> findByAmountType(String keyWord, List<Transaction> transactionList) {
        List<Transaction> foundList = new ArrayList<Transaction>();
        Transaction.TransactionType transactionType = changeKeyToTransType(keyWord);

        for (Transaction transaction : transactionList) {
            if (transaction.getItemType() == transactionType) {
                foundList.add(transaction);
            }
        }

        return foundList;
    }

    /**
     *
     * helper function, change String keyWord into enum TransactionType
     * @param keyWord search key String
     * @return matching enum TransactionType
     */
    private Transaction.TransactionType changeKeyToTransType(String keyWord) {
        keyWord = keyWord.toUpperCase();

        switch (keyWord) {
            case "CREDIT":
                return Transaction.TransactionType.INCOME;
            case "DEBIT":
                return Transaction.TransactionType.EXPENSE;
            default:
                return Transaction.TransactionType.UNDEFINED;
        }
    }
}
