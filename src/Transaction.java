/**
 * abstract super class for Expense and Income
 *
 * CS261 - Assignment #4-#5 - Summer 2017
 * Created by: Noon Pokaratsiri
 * Version: 08/13/2017
 *
 */
abstract class Transaction {
    public enum TransactionType {
        UNDEFINED, EXPENSE, INCOME
    }

    private String          itemNumber;
    private Descriptor      itemDescription;
    private double          amount;
    private TransactionType itemType;

    private static int   numTransactions = 0;

    /**
     * Default constructor: increment numTransactions, set itemNumber and itemDescription, and amount = 0
     */
    Transaction() {
        numTransactions++;
        itemNumber = Integer.toUnsignedString(numTransactions); //unique Id as it will change based on how many Transactions have been created
        itemDescription = new Descriptor();
        amount = 0;
        itemType = TransactionType.UNDEFINED;
    }

    /**
     * Constructor with one double argument
     * use default constructor for the itemDescription
     * @param anAmount a double, amount of the transaction
     */
    Transaction(double anAmount){
        numTransactions++;
        itemNumber = Integer.toUnsignedString(numTransactions);
        itemDescription = new Descriptor();
        amount = anAmount;
        itemType = TransactionType.UNDEFINED;

    }

    /**
     * Constructor with parameters to specify category, description, and amount
     * @param aCategory a String, utilized as a parameter for the Descriptor object constructor
     * @param aDescription a String, utilized as a parameter for the Descriptor object constructor
     * @param anAmount double, amount of the transaction
     */
    Transaction(String aCategory, String aDescription, double anAmount) {
        numTransactions++;
        itemNumber = Integer.toUnsignedString(numTransactions);
        itemDescription = new Descriptor(aCategory, aDescription);
        amount = anAmount;
        itemType = TransactionType.UNDEFINED;

    }

    /**
     *
     * @param aCategory
     * @param aDescription
     */
    public void setItemDescription(String aCategory, String aDescription) {
        this.itemDescription = new Descriptor(aCategory, aDescription);
    }

    public void setItemType() {
        if (this instanceof Expense || this.getAmount() < 0)
            this.itemType = TransactionType.EXPENSE;
        if (this instanceof Income || this.getAmount() > 0)
            this.itemType = TransactionType.INCOME;
    }

    public TransactionType getItemType() {
        return itemType;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     *
     * @return String itemNumber
     */
    public String getItemNumber() {
        return itemNumber;
    }

    /**
     *
     * @return Descriptor itemDescription
     */
    public Descriptor getItemDescription() {
        return itemDescription;
    }

    /**
     *
     * @return double amount
     */
    public double getAmount() {
        return amount;
    }

    public static int getNumTransactions() {
        return numTransactions;
    }

    public void print() {
        System.out.printf(String.format("\n%-5s %-40s %12.2f", itemNumber, itemDescription.toString(), amount));
    }

    @Override
    /**
     * return a string representation of this object
     */
    public String toString() {
        return "Item No: " + getItemNumber() + "\nDescription: " + getItemDescription() +
                "\nAmount: " + getAmount();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Transaction))
            return false;
        Transaction aTransaction = (Transaction) obj;
        return this.getItemDescription().equals(aTransaction.getItemDescription());
        // Descriptor.equals is also overloaded
    }
}
