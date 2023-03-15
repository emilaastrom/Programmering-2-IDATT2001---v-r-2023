package no.ntnu.idatt1002.demo.data;

import java.util.HashMap;
import java.util.Map;

public class Type {
    private double totalIncome;
    private double totalExpense;

    private final Map<String, Double> expenses;
    private final Map<String, Double> incomes;


    public Type() {
        this.expenses = new HashMap<>();
        this.incomes = new HashMap<>();
    }

    public void addIncome(String income, double value) {
        totalIncome += value;
        incomes.put(income, value);
    }

    public void removeIncome(String income) {
        totalIncome -= incomes.get(income);
        incomes.remove(income);
    }

    public void addExpense(String expense, double value) {
        totalExpense += value;
        expenses.put(expense, value);
    }

    public void removeExpense(String expense) {
        totalExpense -= expenses.get(expense);
        expenses.remove(expense);
    }


    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

}
