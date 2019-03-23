package com.company;

import com.company.translatorStrategy.Translator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaseTranslator implements Translator {
    @Override
    public boolean isApplicableToRAPID(String instruction) {
        return instruction.startsWith("TEST");
    }

    @Override
    public boolean isApplicableToVPlus(String instruction) {
        return instruction.startsWith("CASE");
    }

    @Override
    public void translateRAPID(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("(?<=\\bCASE\\s)(\\w+)");
        Matcher m = p.matcher(instructions.get(index));
        String testValue = new String();
        while (m.find()) {
            testValue = m.group();
            break;
        }
        System.out.println(testValue);
        instructions.set(index, "CASE " + testValue + " OF");
    }

    @Override
    public void translateVPlus(List<String> instructions, Integer index) {
        Pattern p = Pattern.compile("(?<=\\bCASE\\s)(\\w+)");
        Matcher m = p.matcher(instructions.get(index));
        String testValue = new String();
        while (m.find()) {
            testValue = m.group();
            break;
        }
        instructions.set(index, "TEST " + testValue);

    }
}
