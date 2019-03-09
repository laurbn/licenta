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
            conditionValue.add(getWhileConditionValue(instructions, index, p));
                for (int i = 0; i < translators.size(); i++) {
                    Translator translator = translators.get(i);
                    if (translator.isApplicableToRAPID(conditionValue.get(0))) {
                        translator.translateRAPID(instructions, index);
                        break;
                    }
                }
                instructions.set(index, "WHILE " + instructions.get(index) + " DO");

        }


        @Override
        public void translateVPlus(List<String> instructions, Integer index) {
            Pattern p = Pattern.compile("(?<=(WHILE ))(.+?)(?=( DO))");
            Matcher m = p.matcher(instructions.get(index));
            List<String> conditionValue = new ArrayList<>();
            conditionValue.add(getWhileConditionValue(instructions, index, p));
            for (int i = 0; i < translators.size(); i++) {
                Translator translator = translators.get(i);
                if (translator.isApplicableToVPlus(conditionValue.get(0))) {
                    translator.translateVPlus(instructions, index);
                    break;
                }
            }
            instructions.set(index, "WHILE " + instructions.get(index) + " DO");

        }
    private String getWhileConditionValue(List<String> instructions, Integer index, Pattern p) {
        Matcher m = p.matcher(instructions.get(index));
        String value = new String();
        while (m.find()) {
            value = m.group();
            break;
        }
        return value;
    }
}
