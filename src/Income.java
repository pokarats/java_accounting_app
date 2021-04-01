/**
 * All credit transactions or where transaction amount is > 0
 *
 * CS261 - Assignment #4-#5 - Summer 2017
 * Created by: Noon Pokaratsiri
 * Version: 08/13/2017
 */
public class Income extends Transaction {

    Income() {
        super();
        super.setItemType();
    }

    Income(double anAmount) {
        super(anAmount);
        setAmount(anAmount);
        super.setItemType();

    }

    Income(String aCategory, String aDescription, double anAmount) {
        super(aCategory, aDescription, anAmount);
        setAmount(anAmount);
        super.setItemType();
    }

    @Override
    public void setAmount(double anAmount) {
        if(anAmount >= 0)
            super.setAmount(anAmount);
        else
            super.setAmount(-anAmount);
    }

    @Override
    public String toString() {
        return "Item No: " + super.getItemNumber() + "\n"
                + "Description: " + super.getItemDescription().toString() + "\n"
                + "Amount: " + super.getAmount();
    }
}

