package mini.project.monthly_budget.model;

import java.io.Serializable;

import jakarta.json.JsonObject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Details implements Serializable {
    
    @NotBlank(message="Please state your name")
    private String name;

    @NotBlank(message="Please state the month and year")
    private String timePeriod;

    @Min(value=1, message="How much is your goal amount? (At least $1)")
    private float goal;

    @Min(value=1, message="Income must be more than 1")
    private float income;

    private float totalExpenses;

    public float getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(float totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public float getGoal() {
        return goal;
    }

    public void setGoal(float goal) {
        this.goal = goal;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public static Details createFromJSON(JsonObject o) {
        Details details = new Details();
        details.setName(o.getString("name"));
        details.setTimePeriod(o.getString("timePeriod"));
        details.setTotalExpenses((float)o.getJsonNumber("totalExpenses").doubleValue());
        details.setIncome((float)o.getJsonNumber("income").doubleValue());
        details.setGoal((float)o.getJsonNumber("goal").doubleValue());
        return details;
    }

}
