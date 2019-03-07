package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//FROM VPlus TO Rapid


public class NestedStatements {


    public static void solveNesting(List<String> instructions) {

        List<String> nestedInstructions = new ArrayList<>();
        for (String instruction : instructions) {
            if (instruction.startsWith("FOR"))
                nestedInstructions.add("FOR");
            if (instruction.startsWith("WHILE"))
                nestedInstructions.add("WHILE");
            if (instruction.startsWith("IF"))
                nestedInstructions.add("IF");
            if (instruction.startsWith("CASE"))
                nestedInstructions.add("TEST");
            if (instruction.startsWith("DO"))
                nestedInstructions.add("DO");
            if (instruction.startsWith("CASE"))
                nestedInstructions.add("TEST");
            if (instruction.startsWith("END"))
                nestedInstructions.add("END");
        }

        List<Integer> tracker = new ArrayList<>();
        tracker.add(1);

        System.out.println("nestedInstructions content: " + nestedInstructions);
        Collections.reverse(nestedInstructions);
        System.out.println("after reversing " + nestedInstructions);
        System.out.println("nestedInstructions size:" + nestedInstructions.size());

        for (int i = 1; i < nestedInstructions.size(); i++) {
            tracker.add(0);
            if ((nestedInstructions.get(i).contains("FOR")) || (nestedInstructions.contains("WHILE")) || (nestedInstructions.get(i).contains("IF")) || (nestedInstructions.get(i).contains("TEST")) || (nestedInstructions.get(i).contains("DO")))
                tracker.set(i, tracker.get(i - 1) - 1);
            if (nestedInstructions.get(i).contains("END"))
                tracker.set(i, tracker.get(i - 1) + 1);
        }

        System.out.println("tracker list: " + tracker);

        int i = 0, j = 1;
        int removedInstructions = 0;
        int iterations = nestedInstructions.size();
        int passedAnotherEnd = 1;
        for (int k = 1; k < iterations; k++) {
            if (tracker.get(i) > tracker.get(j)) {
                String merge = nestedInstructions.get(i);
                String intoOne = nestedInstructions.get(j);
                merge = merge.concat(intoOne);
                nestedInstructions.set(i, merge);
                nestedInstructions.remove(j);
                tracker.remove(j);
                if (passedAnotherEnd == 1)
                    i = i - 1 - removedInstructions;
                else
                    i = i - 1;
                removedInstructions++;
                passedAnotherEnd = 0;
            } else {
                if (tracker.get(i) < tracker.get(j)) {
                    i = i + 1 + removedInstructions;
                    j = j + 1;
                    passedAnotherEnd = 1;
                }
            }
            System.out.println("nestedInstructions content after algoRITM PLS WORK: " + nestedInstructions);
        }
        System.out.println("nestedInstructions content after algoRITM PLS WORK: " + nestedInstructions);

        //compare with the list from arguments
        Collections.reverse(nestedInstructions);
        int currentTransformedEND = 0;
        for (int i1 = 0; i1 < instructions.size(); i1++) {
            String instruction = instructions.get(i1);
            if (instruction.startsWith("END")) {
                instructions.set(i1,nestedInstructions.get(currentTransformedEND));
                currentTransformedEND++;
            }

        }
    }
}

