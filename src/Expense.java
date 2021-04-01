/**
 * all debit transactions are Expenses, transactions with negative amount
 * same as Transaction except is a deductible field
 *
 * CS261 - Assignment #4-#5 - Summer 2017
 * Created by: Noon Pokaratsiri
 * Version: 08/13/2017
 */
public class Expense extends Transaction {
    private boolean             deductible;

    Expense() {
        super();
        setAmount(super.getAmount());
        deductible = false;
        super.setItemType();

    }

    Expense(double anAmount) {
        super(anAmount);
        setAmount(anAmount);
        deductible = false;
        super.setItemType();
    }

    Expense(String aCategory, String aDescription, double anAmount) {
        super(aCategory, aDescription, anAmount);
        setAmount(anAmount);
        deductible = false;
        super.setItemType();
    }

    /**
     * overide setAmount function so that amount is always negative for Expense class
     * @param anAmount
     */
    @Override
    public void setAmount(double anAmount){
        if(anAmount <= 0)
            super.setAmount(anAmount);
        else
            super.setAmount(-anAmount);
    }

    public void setDeductible(String deductible) {
        this.deductible = Boolean.parseBoolean(deductible);
    }

    public boolean isDeductible() {
        return deductible;
    }

    @Override
    public void print() {
        super.print();
        System.out.println(String.format("%18s", (deductible ? "\tDeductible" : "\t")));
    }

    @Override
    public String toString() {
        return "Item No: " + super.getItemNumber() + "\n"
                + "Description: " + super.getItemDescription().toString() + "\n"
                + "Amount: " + super.getAmount() + "\t" + (deductible ? "Deductible" : "\t");
    }
}
