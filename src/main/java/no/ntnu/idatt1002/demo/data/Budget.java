package no.ntnu.idatt1002.demo.data;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Budget {
    private String username;

    private ArrayList<Income> incomeList;

    private ArrayList<Expense> expenseList;
    public Budget(String username) {
        this.username = username;
        this.incomeList = new ArrayList<>();
        this.expenseList = new ArrayList<>();

        try {
            BufferedWriter constructorWriter = new BufferedWriter(new FileWriter(username + "Budget.txt"));
            if (!new File(username + "Budget.txt").isFile()){
                constructorWriter.write("Budget for " + username + " created at " + Date.from(Instant.now()) + "\n");
            }
            constructorWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

       /* try {
            FileWriter myWriter = new FileWriter(username + "Budget.txt");
            myWriter.write("Budget for " + username + " created at " + Date.from(Instant.now()));
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }*/

    }

    public void addIncome(String income, double value) {

        Income newIncome = new Income(income, value);
        incomeList.add(newIncome);

        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(username + "Budget.txt", true));
            myWriter.newLine();
            myWriter.write("Income: " + income + "\nValue: " + value);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void addExpense(String expense, double value) {
        Expense newExpense = new Expense(expense, value);
        expenseList.add(newExpense);

        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(username + "Budget.txt", true));
            myWriter.newLine();
            myWriter.write("Expense: " + expense + "\nValue: " + value);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void removeIncome(String income) {
        for (Income i : incomeList) {
            if (i.getIncome().equals(income)) {
                incomeList.remove(i);

                /*try{
                    File inputFile = new File(username + "Budget.txt");
                    File tempFile = new File("myTempFile.txt");

                    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String currentLine;

                    while((currentLine = reader.readLine()) != null) {
                        String trimmedLine = currentLine.trim();
                        if(trimmedLine.equals(income)) continue;
                        writer.write(currentLine + System.getProperty("line.separator"));
                    }
                    inputFile.delete();
                    boolean successful = tempFile.renameTo(inputFile);
                    writer.close();
                    reader.close();
                }
                catch (IOException e) {
                    System.out.println("An error while trying to remove the income from the users text file.");
                    e.printStackTrace();
                }*/

                //V2 of removing income from the users text file
                try {
                    File inFile = new File(username + "Budget.txt");
                    File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

                    BufferedReader br = new BufferedReader(new FileReader(inFile));
                    PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

                    if (!inFile.isFile()) {
                        System.out.println("Parameter is not an existing file");
                        return;
                    }

                    String line = null;

                    //Read from the original file and write to the new
                    //unless content matches data to be removed.
                    while ((line = br.readLine()) != null) {

                        if (!line.trim().equals(income)) {
                            pw.println(line);
                            pw.flush();
                        }
                    }
                    pw.close();
                    br.close();

                    //Delete the original file
                    if (!inFile.delete()) {
                        System.out.println("Could not delete file");
                        return;
                    }

                    //Rename the new file to the filename the original file had.
                    if (!tempFile.renameTo(inFile))
                        System.out.println("Could not rename file");

                }
                catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    public void removeExpense(String expense) {
        for (Expense e : expenseList) {
            if (e.getExpenseName().equals(expense)) {
                expenseList.remove(e);

                try {
                    File inFile = new File(username + "Budget.txt");
                    File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

                    BufferedReader br = new BufferedReader(new FileReader(inFile));
                    PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

                    if (!inFile.isFile()) {
                        System.out.println("Parameter is not an existing file");
                        return;
                    }

                    String line = null;

                    //Read from the original file and write to the new
                    //unless content matches data to be removed.
                    while ((line = br.readLine()) != null) {

                        if (!line.trim().equals(expense)) {
                            pw.println(line);
                            pw.flush();
                        }
                    }
                    pw.close();
                    br.close();

                    //Delete the original file
                    if (!inFile.delete()) {
                        System.out.println("Could not delete file");
                        return;
                    }

                    //Rename the new file to the filename the original file had.
                    if (!tempFile.renameTo(inFile))
                        System.out.println("Could not rename file");

                }
                catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
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

    public ArrayList<Income> getIncomeList() {
        ArrayList<Income> copyIncomeList = new ArrayList<>();
        copyIncomeList = this.incomeList;
        return copyIncomeList;
    }

    public ArrayList<Expense> getExpenseList() {
        ArrayList<Expense> copyExpenseList = new ArrayList<>();
        copyExpenseList = this.expenseList;
        return expenseList;
    }

}
