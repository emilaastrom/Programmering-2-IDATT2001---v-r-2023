package no.ntnu.idatt1002.demo.data;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Budget {
    private String username;

    private ArrayList<BudgetItem> incomeList;

    private ArrayList<BudgetItem> expenseList;
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

        BudgetItem newIncome = new BudgetItem(income, value);
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
        BudgetItem newExpense = new BudgetItem(expense, value);
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
        for (BudgetItem i : incomeList) {
            if (i.getBudgetItemName().equals(income)) {
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
        for (BudgetItem e : expenseList) {
            if (e.getBudgetItemName().equals(expense)) {
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
        for (BudgetItem i : incomeList) {
            if (i.getBudgetItemName().equals(income)) {
                i.setBudgetItemValue(value);
                break;
            }
        }
    }

    public void editExpense(String expense, double value) {
        for (BudgetItem e : expenseList) {
            if (e.getBudgetItemName().equals(expense)) {
                e.setBudgetItemValue(value);
                break;
            }
        }
    }

    public double getExpenseValue(String expenseName){
        for (BudgetItem e : expenseList) {
            if (e.getBudgetItemName().equals(expenseName)) {
                return e.getBudgetItemValue();
            }
        }
        return -1;
    }

    public double getIncomeValue(String incomeName){
        for (BudgetItem i : incomeList) {
            if (i.getBudgetItemName().equals(incomeName)) {
                return i.getBudgetItemValue();
            }
        }
        return -1;
    }

    public double getTotalIncome() {
        double totalIncome = 0;
        for (BudgetItem i : incomeList) {
            totalIncome += i.getBudgetItemValue();
        }
        return totalIncome;
    }

    public double getTotalExpense() {
        double totalExpense = 0;
        for (BudgetItem e : expenseList) {
            totalExpense += e.getBudgetItemValue();
        }
        return totalExpense;
    }

    public ArrayList<BudgetItem> getIncomeList() {
        ArrayList<BudgetItem> copyIncomeList = new ArrayList<>();
        copyIncomeList = this.incomeList;
        return copyIncomeList;
    }

    public ArrayList<BudgetItem> getExpenseList() {
        ArrayList<BudgetItem> copyExpenseList = new ArrayList<>();
        copyExpenseList = this.expenseList;
        return expenseList;
    }

    public String getUsername() {
        return username;
    }
}
