package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;

public class Budget {
    private String username;
    private ArrayList<Income> incomeList;
    private ArrayList<Expense> expenseList;

    public Budget(String username) {
        this.username = username;
        this.incomeList = new ArrayList<>();
        this.expenseList = new ArrayList<>();
    }

    public void addIncome(String income, double value) {
        Income newIncome = new Income(income, value);
        incomeList.add(newIncome);
    }

    public void addExpense(String expense, double value) {
        Expense newExpense = new Expense(expense, value);
        expenseList.add(newExpense);
    }

    public void removeIncome(String income) {
        for (Income i : incomeList) {
            if (i.getIncome().equals(income)) {
                incomeList.remove(i);
                break;
            }
        }
    }

    public void removeExpense(String expense) {
        for (Expense e : expenseList) {
            if (e.getExpenseName().equals(expense)) {
                expenseList.remove(e);
                break;
            }
        }
    }

    public void editIncome(String income, double value) {
        for (Income i : incomeList) {
            if (i.getIncome().equals(income)) {
                i.setIncomeValue(value);
                break;
            }
        }
    }

    public void editExpense(String expense, double value) {
        for (Expense e : expenseList) {
            if (e.getExpenseName().equals(expense)) {
                e.setExpenseValue(value);
                break;
            }
        }
    }

    public double getExpenseValue(String expenseName){
        for (Expense e : expenseList) {
            if (e.getExpenseName().equals(expenseName)) {
                return e.getExpenseValue();
            }
        }
        return -1;
    }

    public double getIncomeValue(String incomeName){
        for (Income i : incomeList) {
            if (i.getIncome().equals(incomeName)) {
                return i.getIncomeValue();
            }
        }
        return -1;
    }

    public double getTotalIncome() {
        double totalIncome = 0;
        for (Income i : incomeList) {
            totalIncome += i.getIncomeValue();
        }
        return totalIncome;
    }

    public double getTotalExpense() {
        double totalExpense = 0;
        for (Expense e : expenseList) {
            totalExpense += e.getExpenseValue();
        }
        return totalExpense;
    }

}
