package no.ntnu.idatt1002.budgetingapplication.data;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 * The Budget class.
 */
public class Budget {
    private String username;

    private ArrayList<BudgetItem> incomeList;

    private ArrayList<BudgetItem> expenseList;

    /**
     * Instantiates a new Budget.
     *
     * @param username the username of the given user to create a budget for
     */
    public Budget(String username) {
        this.username = username;
        this.incomeList = new ArrayList<>();
        this.expenseList = new ArrayList<>();


        try {
            //Accessing file, or creating a new file if it doesn't exist
            File file = new File(username + "Budget.txt");
            if (!file.exists()) {file.createNewFile();}

            //Reading the file, and adding the top line if it doesn't exist
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            if (line == null || !line.contains("Budget for") || line.isBlank()) {
                BufferedWriter constructorWriter = new BufferedWriter(new FileWriter(file));
                System.out.println("Creating new profile!");
                System.out.println(reader.readLine());
                constructorWriter.write("Budget for " + username + " created at " + Date.from(Instant.now()) + "\n");
                constructorWriter.close();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adding income to the incomeList and writing it to the users budget text file as well.
     *
     * @param income the income
     * @param value  the value
     */
    public void addIncome(String income, double value) {

        BudgetItem newIncome = new BudgetItem(income.substring(0, 1).toUpperCase() + income.substring(1), value);
        incomeList.add(newIncome);

        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(username + "Budget.txt", true));
            myWriter.newLine();
            myWriter.write("Income: " + income.substring(0, 1).toUpperCase() + income.substring(1).toLowerCase() + "\nValue: " + value);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * Add to incomeList but do not write to file.
     *
     * @param income the income
     * @param value  the value
     */
    public void addIncomeNotToFile(String income, double value){
        //Creating a new BudgetItem for the given income and ensuring the name is formatted properly
        BudgetItem newIncome = new BudgetItem(income.substring(0, 1).toUpperCase() + income.substring(1), value);
        incomeList.add(newIncome);
    }

    /**
     * Add expense to expenseList but not to file.
     *
     * @param expense the expense
     * @param value   the value
     */
    public void addExpenseNotToFile(String expense, double value){
        //Creating a new BudgetItem for the given expense and ensuring the name is formatted properly
        BudgetItem newExpense = new BudgetItem(expense.substring(0, 1).toUpperCase() + expense.substring(1), value);
        expenseList.add(newExpense);
    }

    /**
     * Add expense to expenseList and write to users text file.
     *
     * @param expense the expense
     * @param value   the value
     */
    public void addExpense(String expense, double value) {
        //Creating a new BudgetItem for the expense and ensuring the name is formatted properly, and adding it to expenseList
        BudgetItem newExpense = new BudgetItem(expense.substring(0, 1).toUpperCase() + expense.substring(1), value);
        expenseList.add(newExpense);

        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(username + "Budget.txt", true));
            myWriter.newLine();
            myWriter.write("Expense: " + expense.substring(0,1).toUpperCase() + expense.substring(1) + "\nValue: " + value);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     * Empties the budget file, removing all incomes and expenses.
     *
     * @param username name of the user whose budget file is to be cleared
     */
    public void clearBudget(String username){
        try {
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(username + "Budget.txt"));
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Remove income.
     *
     * @param income the income
     */
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
                        System.out.println("The file does not exist!");
                        return;
                    }

                    String line;

                    //Read from the original file and write to the new
                    //unless content matches data to be removed.
                    while ((line = br.readLine()) != null) {
                        if (line.replaceFirst("Income: ", "").trim().equals(income)) {
                            br.readLine();
                        } else if(line.isBlank()){
                            //line = br.readLine();
                            continue;
                        } else {
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
                    System.out.println("File not found!");
                    ex.printStackTrace();
                }
                catch (IOException ex) {
                    System.out.println("IO Exception!");
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * Remove expense.
     *
     * @param expense the expense
     */
    public void removeExpense(String expense) {
        for (BudgetItem e : expenseList) {
            if (e.getBudgetItemName().equals(expense)) {
                expenseList.remove(e);

                try {
                    //Getting current file and creating a temporary file
                    File inFile = new File(username + "Budget.txt");
                    File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

                    //Reader/writer used to read and copy text to replace the current file
                    BufferedReader br = new BufferedReader(new FileReader(inFile));
                    PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

                    //Checking to see if the given user budget file exists
                    if (!inFile.isFile()) {
                        System.out.println("Parameter is not an existing file");
                        return;
                    }


                    //Read from the original file and write to the new, unless content matches expense to be removed.
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.replaceFirst("Expense: ", "").trim().equals(expense)) {
                            br.readLine();
                        } else if(line.isBlank()){
                            //br.readLine();
                            continue;
                        } else {
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

                    //Rename the new file to the original file name to complete the replacement.
                    if (!tempFile.renameTo(inFile))
                        System.out.println("Could not rename file");

                } catch (IOException ex ) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * Edit income.
     *
     * @param income the income
     * @param value  the value
     */
    public void editIncome(String income, double value) {
        for (BudgetItem i : incomeList) {
            if (i.getBudgetItemName().equals(income)) {
                i.setBudgetItemValue(value);
                break;
            }
        }
    }

    /**
     * Edit expense.
     *
     * @param expense the expense
     * @param value   the value
     */
    public void editExpense(String expense, double value) {
        for (BudgetItem e : expenseList) {
            if (e.getBudgetItemName().equals(expense)) {
                e.setBudgetItemValue(value);
                break;
            }
        }
    }

    /**
     * Get expense value double.
     *
     * @param expenseName the expense name
     * @return the double
     */
    public double getExpenseValue(String expenseName){
        for (BudgetItem e : expenseList) {
            if (e.getBudgetItemName().equals(expenseName)) {
                return e.getBudgetItemValue();
            }
        }
        //returns -1 if the expense is not found
        return -1;
    }

    /**
     * Get income value double.
     *
     * @param incomeName the income name
     * @return the double
     */
    public double getIncomeValue(String incomeName){
        for (BudgetItem i : incomeList) {
            if (i.getBudgetItemName().equals(incomeName)) {
                return i.getBudgetItemValue();
            }
        }
        //returns -1 if the expense is not found
        return -1;
    }

    /**
     * Gets total income.
     *
     * @return the total income
     */
    public double getTotalIncome() {
        double totalIncome = 0;
        for (BudgetItem i : incomeList) {
            totalIncome += i.getBudgetItemValue();
        }
        return totalIncome;
    }

    /**
     * Gets total expense.
     *
     * @return the total expense
     */
    public double getTotalExpense() {
        double totalExpense = 0;
        for (BudgetItem e : expenseList) {
            totalExpense += e.getBudgetItemValue();
        }
        return totalExpense;
    }

    /**
     * Gets income list.
     *
     * @return the income list
     */
    public ArrayList<BudgetItem> getIncomeList() {
        return this.incomeList;
    }

    /**
     * Gets expense list.
     *
     * @return the expense list
     */
    public ArrayList<BudgetItem> getExpenseList() {
        return expenseList;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
