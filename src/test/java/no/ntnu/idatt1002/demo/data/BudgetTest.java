package no.ntnu.idatt1002.demo.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {

    Budget testBudget;

    @BeforeEach
    void setUp() {
        testBudget = new Budget("testUser");
        testBudget.addIncome("Jobb", 25000);
        testBudget.addIncome("Ekstrajobb", 10000);
        testBudget.addExpense("Mat", 5000);
        testBudget.addExpense("Bolig", 10000);
        testBudget.addExpense("Transport", 5000);
    }

    @Test
    void addIncome() {
        testBudget.addIncome("TestLønn", 25000);
        assertEquals(25000, testBudget.getIncomeValue("TestLønn"));

        testBudget.addIncome("TestLønn2", 25000);
        assertNotEquals(300, testBudget.getIncomeValue("TestLønn2"));

        assertEquals(testBudget.getIncomeList().size(), 4);
    }

    @Test
    void addExpense() {
        testBudget.addExpense("TestUtgift", 25000);
        assertEquals(25000, testBudget.getExpenseValue("TestUtgift"));

        testBudget.addExpense("TestUtgift2", 25000);
        assertNotEquals(300, testBudget.getExpenseValue("TestUtgift2"));

        assertEquals(testBudget.getExpenseList().size(), 5);
    }

    @Test
    void removeIncome() {
        assertEquals(25000, testBudget.getIncomeValue("Jobb"));

        testBudget.removeIncome("Jobb");
        assertNotEquals(25000, testBudget.getIncomeValue("Ekstrajobb"));

        // Forventer -1 som betyr at inntekten ikke finnes
        testBudget.removeIncome("Ekstrajobb");
        assertEquals((-1), testBudget.getIncomeValue("Ekstrajobb"));
    }

    @Test
    void removeExpense() {
        // Forventer 5000 som betyr at utgiften finnes
        assertEquals(5000, testBudget.getExpenseValue("Mat"));

        testBudget.removeExpense("Mat");
        assertNotEquals(5000, testBudget.getExpenseValue("Mat"));
    }

    @Test
    void editIncome() {
        testBudget.editIncome("Jobb", 100);
        assertEquals(100, testBudget.getIncomeValue("Jobb"));
    }

    @Test
    void editExpense() {
        testBudget.editExpense("Mat", 200);
        assertEquals(200, testBudget.getExpenseValue("Mat"));
    }

    @Test
    void getExpenseValue() {
        assertEquals(10000, testBudget.getExpenseValue("Bolig"));
    }

    @Test
    void getIncomeValue() {
        assertEquals(10000, testBudget.getIncomeValue("Ekstrajobb"));
    }

    @Test
    void getTotalIncome() {
        assertEquals(35000, testBudget.getTotalIncome());

        testBudget.addIncome("Testjobb", 4000);
        testBudget.removeIncome("Ekstrajobb");
        testBudget.editIncome("Testjobb", 200);

        assertEquals(25200, testBudget.getTotalIncome());
    }

    @Test
    void getTotalExpense() {
        assertEquals(20000, testBudget.getTotalExpense());

        testBudget.addExpense("Testkostnader", 2000);
        testBudget.removeExpense("Bolig");
        testBudget.editExpense("Testkostnader", 0);

        assertEquals(10000, testBudget.getTotalExpense());
    }

    @Test
    void getIncomeList() {
        assertEquals(2, testBudget.getIncomeList().size());
        assertEquals(3, testBudget.getExpenseList().size());

        ArrayList<String> incomeNames = new ArrayList<>();
        ArrayList<Double> incomeValues = new ArrayList<>();

        incomeNames.add("Jobb");
        incomeNames.add("Ekstrajobb");

        incomeValues.add(25000.0);
        incomeValues.add(10000.0);

        for(int i = 0; i < testBudget.getIncomeList().size(); i++) {
            assertEquals(incomeNames.get(i), testBudget.getIncomeList().get(i).getBudgetItemName());
            assertEquals(incomeValues.get(i), testBudget.getIncomeList().get(i).getBudgetItemValue());
        }

    }

    @Test
    void getExpenseList() {
        assertEquals(3, testBudget.getExpenseList().size());

        ArrayList<String> expenseNames = new ArrayList<>();
        ArrayList<Double> expenseValues = new ArrayList<>();

        expenseNames.add("Mat");
        expenseNames.add("Bolig");
        expenseNames.add("Transport");

        expenseValues.add(5000.0);
        expenseValues.add(10000.0);
        expenseValues.add(5000.0);

        for(int i = 0; i < testBudget.getExpenseList().size(); i++) {
            assertEquals(expenseNames.get(i), testBudget.getExpenseList().get(i).getBudgetItemName());
            assertEquals(expenseValues.get(i), testBudget.getExpenseList().get(i).getBudgetItemValue());
        }
    }
}