package mini.project.monthly_budget.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Entry implements Serializable {

    private String refId;

    public float amountToGoal;

    private Details details;

    private Expense expense;

    private Budget budget;

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Entry(Details details, Budget budget) {
        this.details = details;
        this.budget = budget;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public float getAmountToGoal() {
        return amountToGoal;
    }

    public void setAmountToGoal(float amountToGoal) {
        this.amountToGoal = amountToGoal;
    }

    // public String getAmountToGoal() {
    //     return amountToGoal;
    // }

    // public void setAmountToGoal(String amountToGoal) {
    //     this.amountToGoal = amountToGoal;
    // }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
            .add("refId", this.getRefId())
            .add("name", this.getDetails().getName())
            .add("timePeriod", this.getDetails().getTimePeriod())
            .add("totalExpenses", this.getDetails().getTotalExpenses())
            .add("income", this.getDetails().getIncome())
            .add("goal", this.getDetails().getGoal())
            .add("amountToGoal", this.getAmountToGoal())
            .build();
    }

    public static Entry createFromJSON(String json) throws IOException {
        InputStream is = new ByteArrayInputStream(json.getBytes());
        JsonReader r = Json.createReader(is);
        JsonObject o = r.readObject();
        Budget budget = Budget.createFromJSON(o);
        Details details = Details.createFromJSON(o);
        Entry entry = new Entry(details, budget);
        entry.setRefId(o.getString("refId"));
        entry.setAmountToGoal((float)o.getJsonNumber("amountToGoal").doubleValue());
        // entry.setAmountToGoal(o.getString("amountToGoal"));
        return entry;
    }
    
}
