package no.ntnu.idatt1002.demo.data;

public class Income {
    private final String income;
    private double incomeValue;

    Income(String income, double incomeValue){
        this.income = income;
        this.incomeValue = incomeValue;
    }

    public String getIncome() {
        return income;
    }

    public double getIncomeValue() {
        return incomeValue;
    }

    public void setIncomeValue(double incomeValue) {
        this.incomeValue = incomeValue;
    }
}
