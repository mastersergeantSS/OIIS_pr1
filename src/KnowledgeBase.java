import java.util.*;

public class KnowledgeBase {
    Map<List<String>, String> rules = new HashMap<>();


    public void addRule(List<String> conditions, String action) {
        rules.put(conditions, action);
    }

    public List<String> getActionList() {
        return List.copyOf(rules.values());
    }

    public Map<List<String>, String> getRules() {
        return rules;
    }
}
