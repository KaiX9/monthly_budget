package mini.project.monthly_budget.model;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Expense implements Serializable {
    
    @NotNull(message="Please choose an expense option")
    private String expenses;

    @Min(value=1, message="Expenses must be more than 1")
    private float expenseCost;

    public String getExpenses() {
        return expenses;
    }

    public void setExpenses(String expenses) {
        this.expenses = expenses;
    }

    public float getExpenseCost() {
        return expenseCost;
    }

    public void setExpenseCost(float expenseCost) {
        this.expenseCost = expenseCost;
    }

    public void addCost(float expenseCost) {
        this.expenseCost += expenseCost;
    }

    public void addCost() {
        this.expenseCost++;
    }

}
