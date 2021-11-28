import java.util.ArrayList;
import java.util.List;

public class WorkingMemory {
    List<String> factsPool = new ArrayList<>();
    List<String> activeFacts = new ArrayList<>();

    public void addFactToPool(String fact) {
        if (!factsPool.contains(fact))
        factsPool.add(fact);
    }

    public void addFact(String fact) {
        if (!activeFacts.contains(fact))
            activeFacts.add(fact);
    }

    public List<String> getFacts() {
        return activeFacts;
    }
}
