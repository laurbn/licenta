package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BorTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.contains("BitOr");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.contains("BOR");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("(?<=\\()(.*)(?=,)");
        Matcher m = p.matcher(instructions.get(index));
        String testValue1 = new String();
        while (m.find()) {
            testValue1 = m.group();
            break;
        }
        Pattern p2 = Pattern.compile("(?<=, )(.*)(?=\\))");
        Matcher m2 = p2.matcher(instructions.get(index));
        String testValue2 = new String();
        while (m2.find()) {
            testValue2 = m2.group();

            break;
        }
        System.out.println(testValue1);
        System.out.println(testValue2);
        instructions.set(index, testValue1 + " BOR " + testValue2);
    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("\\w+(?=\\s+BOR)");
        Matcher m = p.matcher(instructions.get(index));
        String testValue1 = new String();
        while (m.find()) {
            testValue1 = m.group();
            break;
        }
        Pattern p2 = Pattern.compile("(?<=\\BOR\\s)(\\w+)$");
        Matcher m2 = p2.matcher(instructions.get(index));
        String testValue2 = new String();
        while (m2.find()) {
            testValue2 = m2.group();
            break;
        }
        System.out.println(testValue1);
        System.out.println(testValue2);
        instructions.set(index, "BitOr("+testValue1+", "+testValue2+")");
    }
}
