package com.company.translatorStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.Main.translators;

public class WhileTranslator implements Translator {
        @Override
        public boolean isApplicableToRAPID(String instruction) {
            return instruction.startsWith("WHILE");
        }

        @Override
        public boolean isApplicableToVPlus(String instruction) {
            return instruction.startsWith("WHILE");
        }

        @Override
        public void translateRAPID(List<String> instructions, Integer index) {
            Pattern p = Pattern.compile("(?<=(WHILE ))(.+?)(?=( DO))");
            Matcher m = p.matcher(instructions.get(index));
            List<String> conditionValue = new ArrayList<>();
            conditionValue.add(getConditionValue(instructions, index, p));
            System.out.println(conditionValue);
            System.out.println(translators.size());
                for (int i = 0; i < translators.size(); i++) {
                    Translator translator = translators.get(i);
                    System.out.println(translators.get(i));
                    if (translator.isApplicableToRAPID(conditionValue.get(0))) {
                        System.out.println("a intrat aici" + i);
                        translator.translateRAPID(instructions, index);
                        break;
                    }
                }
                instructions.set(index, "WHILE " + instructions.get(index) + " DO");
            System.out.println(instructions.get(index));

        }


        @Override
        public void translateVPlus(List<String> instructions, Integer index) {
            Pattern p = Pattern.compile("(?<=(WHILE ))(.+?)(?=( DO))");
            Matcher m = p.matcher(instructions.get(index));
            List<String> conditionValue = new ArrayList<>();
            conditionValue.add(getConditionValue(instructions, index, p));
            for (int i = 0; i < translators.size(); i++) {
                Translator translator = translators.get(i);
                if (translator.isApplicableToVPlus(conditionValue.toString())) {
                    translator.translateRAPID(instructions, index);
                    break;
                }
            }
            instructions.set(index, "WHILE " + instructions.get(index) + " DO");

        }
    private String getConditionValue(List<String> instructions, Integer index, Pattern p) {
        Matcher m = p.matcher(instructions.get(index));
        String value = new String();
        while (m.find()) {
            value = m.group();
            break;
        }
        return value;
    }
}
