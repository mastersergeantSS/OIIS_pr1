import java.util.*;

public class Solver {
    private WorkingMemory workingMemory;
    private KnowledgeBase knowledgeBase;
    private int chainingType;
    private String hypothesis;

    public Solver(WorkingMemory workingMemory, KnowledgeBase knowledgeBase, int chainingType, String hypothesis) {
        this.workingMemory = workingMemory;
        this.knowledgeBase = knowledgeBase;
        this.chainingType = chainingType;
        this.hypothesis = hypothesis;
    }

    public void solve() {
        if (chainingType == 1)
            forwardChaining();
        else
            backwardsChaining();
    }

    private void forwardChaining() {
        Map<List<String>, String> rules = knowledgeBase.getRules();
        Set<List<String>> conditions = rules.keySet();
        List<String> workingMemoryList = workingMemory.getFacts();
        boolean foundConditionFlag = true;
        while (foundConditionFlag) {
            showWorkingMemory(workingMemoryList);
            List<String> suitableCondition = findSuitableRule(workingMemoryList, conditions);

            if (suitableCondition.isEmpty())
                foundConditionFlag = false;
            else {
                workingMemoryList.add(rules.get(suitableCondition));
                conditions.remove(suitableCondition);
            }
        }
        System.out.println("\n\nПолучена рекомендация системы: " + workingMemoryList.get(workingMemoryList.size() - 1));


    }

    private List<String> findSuitableRule(List<String> workingMemory, Set<List<String>> conditions) {

        for (List<String> condition: conditions) {
            if (workingMemory.containsAll(condition)) {
                return condition;
            }
        }
        return new ArrayList<String>();
    }



    private void backwardsChaining() {
        Map<List<String>, String> rules = knowledgeBase.getRules();
        Set<List<String>> conditions = rules.keySet();
        List<String> consequences = List.copyOf(rules.values());
        List<String> workingMemoryList = workingMemory.getFacts();
        Stack<String> goalStack = new Stack<>();
        goalStack.push(hypothesis);

        while (!goalStack.isEmpty()) {
            showWorkingMemory(workingMemoryList);
            String goal = goalStack.peek();
            List<String> goalConditions = findGoalConditions(consequences, goal);

            if (goalConditions.isEmpty()) break;

            if (workingMemoryList.containsAll(goalConditions)) {
                workingMemoryList.add(goal);
                goalStack.pop();
            } else {
                for (String goalCondition: goalConditions) {
                    if (!workingMemoryList.contains(goalCondition)) {
                        goalStack.push(goalCondition);
                        break;
                    }
                }
            }
        }
        showWorkingMemory(workingMemoryList);
        if (workingMemoryList.contains(hypothesis)) {
            System.out.println("\n\nГипотеза '" + hypothesis + "' подтверждена");
        } else {
            System.out.println("\n\nГипотеза '" + hypothesis + "' отвергнута");
        }

    }


    private List<String> findGoalConditions(List<String> consequences, String goal) {
        for (String consequence : consequences) {
            if (consequence.equals(goal)) {
                List<String> goalConditions = getKeyByValue(knowledgeBase.getRules(), consequence);
                return goalConditions;
            }
        }
        return List.of();
    }


    private void showWorkingMemory(List<String> workingMemory) {
        System.out.println("\n\n--------------------------[Рабочая память]-----------------------------------------\n" +
                workingMemory);
    }


    private <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
