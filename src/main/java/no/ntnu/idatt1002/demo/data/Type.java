package no.ntnu.idatt1002.demo.data;

import java.io.*;
import java.util.*;


public class Type {
    private double totalIncome;
    private double totalExpense;
    private int month;
    private int year;

    private final Map<String, List<Double>> expenses;
    private final Map<String, List<Double>> incomes;
    private final Map<String, Double> expensesByCategory;
    private final Map<String, Double> incomesByCategory;
    private File textFile;
    private String strMonth;
    private String strYear;


    public Type(int month, int year) {
        this.expenses = new HashMap<String, List<Double>>();
        this.incomes = new HashMap<String, List<Double>>();
        this.month = month;
        this.year = year;
        this.strMonth = String.valueOf(this.month);
        this.strYear = String.valueOf(this.year);
        this.totalExpense = 0;
        this.totalIncome = 0;
        this.expensesByCategory = new HashMap<String, Double>();
        this.incomesByCategory = new HashMap<String, Double>();

        try {
            File myObj = new File(strMonth+strYear+".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        this.textFile = new File(strMonth+strYear+".txt");


    }

    public void addIncome(String income, double value) {

        List<Double> newValues;
        if(incomes.containsKey(income)) {
            newValues = incomes.get(income);
            newValues.add(value);
        } else {
            newValues = new ArrayList<Double>(Arrays.asList(value));
        }
        incomes.put(income, newValues);

        try {
            FileWriter myWriter = new FileWriter(strMonth+strYear+".txt");
            myWriter.write("income:" + income + "value:" + value);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void removeIncome(String income, double value) {
        List<Double> newValues = incomes.get(income);
        newValues.remove(value);

        if(newValues.isEmpty()) {
            incomes.remove(income);
        } else {
            incomes.put(income, newValues);
        }



        File inputFile = new File(strMonth+strYear+".txt");
        File tempFile = new File("myTempFile.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));


            String lineToRemove = "income:" + income + "value:" + value;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.equals(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void addExpense(String expense, double value) {

        List<Double> newValues;

        if(expenses.containsKey(expense)) {
            newValues = expenses.get(expense);
            newValues.add(value);
        } else {
            newValues = new ArrayList<Double>(Arrays.asList(value));
        }

        expenses.put(expense, newValues);

        try {
            FileWriter myWriter = new FileWriter(this.textFile);
            myWriter.write("expense:" + expense + "value" + value);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void removeExpense(String expense, double value) {
        List<Double> newValues = expenses.get(expense);
        newValues.remove(expense);

        if(newValues.isEmpty()) {
            expenses.remove(expense);
        } else {
            expenses.put(expense, newValues);
        }

        File inputFile = new File(strMonth+strYear+".txt");
        File tempFile = new File("myTempFile.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));


            String lineToRemove = "expense:" + expense + "value" + value;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.equals(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            boolean successful = tempFile.renameTo(inputFile);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public double getTotalIncome() {
        for(List<Double> i: incomes.values()) {
            for (double j : i) {
                totalIncome += j;
            }
        }
        return totalIncome;
    }

    public double getTotalExpense() {
        for(List<Double> i: expenses.values()) {
            for(double j: i) {
                totalExpense += j;
            }
        }
        return totalExpense;
    }

    public List<Double> getIncome(String income) {
        return incomes.get(income);
    }

    public Map<String, Double> getIncomesByCategory() {

        for(String income: incomes.keySet()) {

            double newIncome = 0;

            for(Double value: incomes.get(income)) {
                newIncome += value;
            }
            incomesByCategory.put(income, newIncome);

        }
        return incomesByCategory;
    }

    public Map<String, Double> getExpensesByCategory() {

        for(String expense: expenses.keySet()) {

            double newExpense = 0;

            for(Double value: expenses.get(expense)) {
                newExpense += value;
            }
            expensesByCategory.put(expense, newExpense);

        }
        return expensesByCategory;
    }





}
