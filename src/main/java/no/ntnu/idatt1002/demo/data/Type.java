package no.ntnu.idatt1002.demo.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner; // Import the Scanner class to read text files


public class Type {
    private double totalIncome;
    private double totalExpense;
    private int month;
    private int year;

    private final Map<String, Double> expenses;
    private final Map<String, Double> incomes;
    private File textFile;
    private String strMonth;
    private String strYear;


    public Type(int month, int year) {
        this.expenses = new HashMap<>();
        this.incomes = new HashMap<>();
        this.month = month;
        this.year = year;
        this.strMonth = String.valueOf(this.month);
        this.strYear = String.valueOf(this.year);
        this.totalExpense = 0;
        this.totalIncome = 0;

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
        incomes.put(income, value);

        try {
            FileWriter myWriter = new FileWriter(strMonth+strYear+".txt");
            myWriter.write("income:" + income + "value" + value);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void removeIncome(String income) {
        incomes.remove(income);

        File inputFile = new File(strMonth+strYear+".txt");
        File tempFile = new File("myTempFile.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));


            String lineToRemove = "income:" + income + "value" + incomes.get(income);
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
        expenses.put(expense, value);

        try {
            FileWriter myWriter = new FileWriter(this.textFile);
            myWriter.write("expense:" + expense + "value" + value);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void removeExpense(String expense) {
        expenses.remove(expense);

        File inputFile = new File(strMonth+strYear+".txt");
        File tempFile = new File("myTempFile.txt");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));


            String lineToRemove = "expense:" + expense + "value" + expenses.get(expense);
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
        for(double i: incomes.values()) {
            totalIncome += i;
        }
        return totalIncome;
    }

    public double getTotalExpense() {
        for(double i: expenses.values()) {
            totalExpense += i;
        }
        return totalExpense;
    }

    public double getIncome(String income) {
        return incomes.get(income);
    }

    public double getExpense(String expense) {
        return expenses.get(expense);
    }

    public void setIncome(String income, double value) {
        incomes.put(income, value);
    }

    public void setExpense(String expense, double value) {

        expenses.put(expense, value);
    }



}
