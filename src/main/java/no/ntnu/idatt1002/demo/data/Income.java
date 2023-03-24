package no.ntnu.idatt1002.demo.data;

public class Income {
    private final String incomeName;
    private double incomeValue;

    Income(String incomeName, double incomeValue){
        this.incomeName = incomeName;
        this.incomeValue = incomeValue;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public double getIncomeValue() {
        return incomeValue;
    }

    public void setIncomeValue(double incomeValue) {
        this.incomeValue = incomeValue;
    }
}
