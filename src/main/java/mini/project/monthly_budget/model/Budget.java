package mini.project.monthly_budget.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.JsonObject;

public class Budget implements Serializable {
    
    private List<Expense> expenses = new LinkedList<Expense>();

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense expense) {
        List<Expense> currExpenses = this.expenses.stream()
                .filter(e -> e.getExpenses().equals(expense.getExpenses()))
                .toList();
        if (currExpenses.isEmpty()) {
            this.expenses.add(expense);
        } else {
            currExpenses.get(0).addCost(expense.getExpenseCost());
        }
    }

    public static Budget createFromJSON(JsonObject o) {
        Budget budget = new Budget();
        return budget;
    }

}
