package no.ntnu.idatt1002.demo.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {

    Budget testBudget;

    @BeforeEach
    void setUp() {
        testBudget = new Budget("testUser");
        testBudget.removeIncome("Jobb");
        testBudget.removeIncome("Ekstrajobb");
        testBudget.removeExpense("Mat");
        testBudget.removeExpense("Bolig");
        testBudget.removeExpense("Transport");

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
    }

    @Test
    void addExpense() {
        testBudget.addExpense("TestUtgift", 25000);
        assertEquals(25000, testBudget.getExpenseValue("TestUtgift"));

        testBudget.addExpense("TestUtgift2", 25000);
        assertNotEquals(300, testBudget.getExpenseValue("TestUtgift2"));
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
    }

    @Test
    void editExpense() {
    }

    @Test
    void getExpenseValue() {
    }

    @Test
    void getIncomeValue() {
    }

    @Test
    void getTotalIncome() {
    }

    @Test
    void getTotalExpense() {
    }

    @Test
    void getIncomeList() {
    }

    @Test
    void getExpenseList() {
    }
}