package no.ntnu.idatt1002.demo.data;

public class BudgetItem {
    private final String budgetItemName;
    private double budgetItemValue;

    BudgetItem(String budgetItemName, double budgetItemValue){
        this.budgetItemName = budgetItemName;
        this.budgetItemValue = budgetItemValue;
    }

    public String getBudgetItemName() {
        return budgetItemName;
    }

    public double getBudgetItemValue() {
        return budgetItemValue;
    }

    public void setBudgetItemValue(double incomeValue) {
        this.budgetItemValue = incomeValue;
    }
}
