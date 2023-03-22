package mini.project.monthly_budget.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import mini.project.monthly_budget.model.Entry;
import mini.project.monthly_budget.service.BudgetService;

@RestController
@RequestMapping(path="/entry")
public class BudgetRestController {
    
    @Autowired
    private BudgetService budgetSvc;

    @GetMapping(path="{refId}")
    public ResponseEntity<String> getEntryDetails(@PathVariable String refId) throws IOException {
        Optional<Entry> entry = budgetSvc.getEntryByRefId(refId);
        if (entry.isEmpty()) {
            JsonObject error = Json.createObjectBuilder()
                .add("message", "Entry %s is not found".formatted(refId))
                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.toString());
        }
        return ResponseEntity.ok(entry.get().toJSON().toString());
    }
}
