package mini.project.monthly_budget.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import mini.project.monthly_budget.model.Budget;
import mini.project.monthly_budget.model.Details;
import mini.project.monthly_budget.model.Entry;
import mini.project.monthly_budget.model.Expense;
import mini.project.monthly_budget.repository.BudgetRepo;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepo budgetRepo;
    
    public static final String[] EXPENSE_OPTIONS = {"entertainment", "transportation", "dining", "others"};

    private final Set<String> expenseOption;

    public BudgetService() {
        expenseOption = new HashSet<String>(Arrays.asList(EXPENSE_OPTIONS));
    }

    public List<ObjectError> validateExpense(Expense expense) {
        List<ObjectError> errors = new LinkedList<>();

        if (!expenseOption.contains(expense.getExpenses().toLowerCase())) {
            FieldError e = new FieldError("expense", "expenses", "%s is N/A. Please choose a valid option.".formatted(expense.getExpenses()));
            errors.add(e);
        }
        return errors;
    }

    public Entry createEntry(Details details, Budget budget) {
        String refId = UUID.randomUUID().toString().substring(0, 4);
        Entry entry = new Entry(details, budget);
        entry.setRefId(refId);
        float totalExpenses = 0.00f;
        for (Expense expense : budget.getExpenses()) {
            if (expense.getExpenses().contains("entertainment")) {
                totalExpenses += expense.getExpenseCost();
            }
            if (expense.getExpenses().contains("transportation")) {
                totalExpenses += expense.getExpenseCost();
            }
            if (expense.getExpenses().contains("dining")) {
                totalExpenses += expense.getExpenseCost();
            }
            if (expense.getExpenses().contains("others")) {
                totalExpenses += expense.getExpenseCost();
            }
        }
        details.setTotalExpenses(totalExpenses);
        // if ((details.getGoal() - (details.getIncome() - totalExpenses)) <= 0) {
        //     entry.setAmountToGoal("You have achieved your goal!");
        // } else {
        // entry.setAmountToGoal(String.valueOf(details.getGoal() - (details.getIncome() - totalExpenses)));
        // }
        entry.setAmountToGoal(details.getGoal() - (details.getIncome() - totalExpenses));
        return entry;
    }

    public Entry saveEntry(Details details, Budget budget) {
        Entry entry = createEntry(details, budget);
        budgetRepo.saveEntry(entry);
        return entry;
    }

    public Optional<Entry> getEntryByRefId(String refId) throws IOException {
        return budgetRepo.getEntryDetails(refId);
    }
}
