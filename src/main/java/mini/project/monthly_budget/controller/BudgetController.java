package mini.project.monthly_budget.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import mini.project.monthly_budget.model.Budget;
import mini.project.monthly_budget.model.Details;
import mini.project.monthly_budget.model.Entry;
import mini.project.monthly_budget.model.Expense;
import mini.project.monthly_budget.service.BudgetService;

@Controller
@RequestMapping
public class BudgetController {
    
    @Autowired
    private BudgetService budgetSvc;

    @GetMapping(path={"/", "/index.html"})
    public String addExpenseForm(Model m, HttpSession s) {
        Budget budget = (Budget) s.getAttribute("budget");
        if (null == budget) {
            budget = new Budget();
            s.setAttribute("budget", budget);
        }

        m.addAttribute("expense", new Expense());
        m.addAttribute("budget", budget);

        return "index";
    }

    @PostMapping(path="/expense")
    public String validateForm(Model m, HttpSession s
        , @ModelAttribute @Valid Expense expense, BindingResult binding) {
        Budget budget = (Budget) s.getAttribute("budget");
        if (budget == null) {
            budget = new Budget();
            s.setAttribute("budget", budget);
        }
        m.addAttribute("budget", budget);

        if (binding.hasErrors()) {
            return "index";
        }

        List<ObjectError> errors = budgetSvc.validateExpense(expense);
        if (!errors.isEmpty()) {
            for (ObjectError e : errors) {
                binding.addError(e);
            }
            return "index";
        }
        budget.addExpense(expense);
        m.addAttribute("expense", expense);

        return "index";
    }

    @GetMapping(path="/details")
    public String inputDetails(Model m, HttpSession s) {
        Budget budget = (Budget) s.getAttribute("budget");
        if (budget == null) {
            budget = new Budget();
            s.setAttribute("budget", budget);
    
            return "index";
        }
        
        m.addAttribute("details", new Details());
        
        return "details";
    }

    @PostMapping(path="/save")
    public String saveData(Model m, HttpSession s
        , @ModelAttribute @Valid Details details, BindingResult binding) {
        Budget budget = (Budget) s.getAttribute("budget");
        if (budget == null) {
            budget = new Budget();
            s.setAttribute("budget", budget);

            return "index";
        }
        if (binding.hasErrors()) {
            return "details";
        }
        
        Entry entry = budgetSvc.saveEntry(details, budget);
        m.addAttribute("entry", entry);
        s.invalidate();
        return "confirm";
    }
}
