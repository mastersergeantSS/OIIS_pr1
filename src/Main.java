import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        WorkingMemory workingMemory = new WorkingMemory();
        workingMemory.addFactToPool("Преподаватель строгий");
        workingMemory.addFactToPool("Преподаватель не строгий");
        workingMemory.addFactToPool("Сданы все работы по предмету");
        workingMemory.addFactToPool("Сдана часть работ по предмету");
        workingMemory.addFactToPool("Мало времени до экзамена");
        workingMemory.addFactToPool("Много времени до экзамена");


        KnowledgeBase knowledgeBase = new KnowledgeBase();
        knowledgeBase.addRule(List.of("Много времени до экзамена", "Сдана часть работ по предмету"), "Есть время на подготовку");
        knowledgeBase.addRule(List.of("Сдана часть работ по предмету", "Мало времени до экзамена"), "Нет времени на подготовку");
        knowledgeBase.addRule(List.of("Нет времени на подготовку", "Преподаватель строгий"), "Можно выспаться");
        knowledgeBase.addRule(List.of("Нет времени на подготовку", "Преподаватель не строгий"), "Не спать и готовиться");
        knowledgeBase.addRule(List.of("Сданы все работы по предмету", "Преподаватель не строгий"), "Можно отдыхать до экзамена");
        knowledgeBase.addRule(List.of("Сданы все работы по предмету", "Преподаватель строгий"), "Нужно готовиться к экзамену");

        List<String> activeFacts = readFacts(workingMemory.factsPool);
        for (String fact: activeFacts)
            workingMemory.addFact(fact);

        String hypothesis = "";
        int chainingType = readChainingType(); // 1 - прямой вывод, 2 - обратный вывод
        if (chainingType == 2) {
            List<String> listOfHypotheses = knowledgeBase.getActionList();
            hypothesis = readHypothesis(listOfHypotheses);
        }

        Solver solver = new Solver(workingMemory, knowledgeBase, chainingType, hypothesis);
        solver.solve();


    }



    public static List<String> readFacts(List<String> facts) {
        for (int i = 1; i <= facts.size(); i++) {
            System.out.println(i + ". " + facts.get(i-1));
        }
        System.out.print("Выберите факты через пробел: ");
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        String[] indexes = line.split(" ");
        List<String> chosenFacts = new ArrayList<>();
        for (String index : indexes) {
            int intIndex = Integer.valueOf(index);
            chosenFacts.add(facts.get(intIndex-1));
        }
        return chosenFacts;
    }

    public static int readChainingType() {
        System.out.print("1. Прямой вывод\n" +
                            "2. Обратный вывод\n" +
                            "Выберите тип вывода: ");
        Scanner in = new Scanner(System.in);
        int chainingType = in.nextInt();
        return chainingType;

    }

    public static String readHypothesis(List<String> hypotheses) {
        hypotheses = hypotheses.stream().distinct().collect(Collectors.toList());
        for (int i = 1; i <= hypotheses.size(); i++) {
            System.out.println(i + ". " + hypotheses.get(i-1));
        }

        System.out.print("Выберите номер гипотезы: ");
        Scanner in = new Scanner(System.in);
        String hypothesis = hypotheses.get(in.nextInt() - 1);
        return hypothesis;
    }




}
