package no.ntnu.idatt1002.demo.data;

public class Expense {
    private final String expenseName;
    private double expenseValue;

    Expense(String expenseName, double expenseValue){
        this.expenseName = expenseName;
        this.expenseValue = expenseValue;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public double getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(double incomeValue) {
        this.expenseValue = incomeValue;
    }
}
