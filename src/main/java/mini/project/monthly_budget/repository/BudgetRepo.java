package mini.project.monthly_budget.repository;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import mini.project.monthly_budget.model.Entry;

@Repository
public class BudgetRepo {
    
    @Autowired @Qualifier("budget")
    private RedisTemplate<String, String> template;

    public void saveEntry(Entry entry) {
        this.template.opsForValue().set(entry.getRefId(), entry.toJSON().toString());
    }

    public Optional<Entry> getEntryDetails(String refId) throws IOException {
        String json = template.opsForValue().get(refId);
        if((json == null || json.trim().length() <= 0)) {
            return Optional.empty();
        }
        return Optional.of(Entry.createFromJSON(json));
    }
}
